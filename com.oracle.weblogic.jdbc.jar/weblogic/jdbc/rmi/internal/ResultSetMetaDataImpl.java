package weblogic.jdbc.rmi.internal;

import java.sql.SQLException;

public class ResultSetMetaDataImpl implements ResultSetMetaData {
   private java.sql.ResultSetMetaData t2_rsmd;

   public ResultSetMetaDataImpl(java.sql.ResultSetMetaData rsmd) {
      this.t2_rsmd = null;
      this.t2_rsmd = rsmd;
   }

   public ResultSetMetaDataImpl() {
      this((java.sql.ResultSetMetaData)null);
   }

   public int getColumnCount() throws SQLException {
      return this.t2_rsmd.getColumnCount();
   }

   public boolean isAutoIncrement(int column) throws SQLException {
      return this.t2_rsmd.isAutoIncrement(column);
   }

   public boolean isCaseSensitive(int column) throws SQLException {
      return this.t2_rsmd.isCaseSensitive(column);
   }

   public boolean isSearchable(int column) throws SQLException {
      return this.t2_rsmd.isSearchable(column);
   }

   public boolean isCurrency(int column) throws SQLException {
      return this.t2_rsmd.isCurrency(column);
   }

   public int isNullable(int column) throws SQLException {
      return this.t2_rsmd.isNullable(column);
   }

   public boolean isSigned(int column) throws SQLException {
      return this.t2_rsmd.isSigned(column);
   }

   public int getColumnDisplaySize(int column) throws SQLException {
      return this.t2_rsmd.getColumnDisplaySize(column);
   }

   public String getColumnLabel(int column) throws SQLException {
      return this.t2_rsmd.getColumnLabel(column);
   }

   public String getColumnName(int column) throws SQLException {
      return this.t2_rsmd.getColumnName(column);
   }

   public String getSchemaName(int column) throws SQLException {
      return this.t2_rsmd.getSchemaName(column);
   }

   public int getPrecision(int column) throws SQLException {
      return this.t2_rsmd.getPrecision(column);
   }

   public int getScale(int column) throws SQLException {
      return this.t2_rsmd.getScale(column);
   }

   public String getTableName(int column) throws SQLException {
      return this.t2_rsmd.getTableName(column);
   }

   public String getCatalogName(int column) throws SQLException {
      return this.t2_rsmd.getCatalogName(column);
   }

   public int getColumnType(int column) throws SQLException {
      return this.t2_rsmd.getColumnType(column);
   }

   public String getColumnTypeName(int column) throws SQLException {
      return this.t2_rsmd.getColumnTypeName(column);
   }

   public boolean isReadOnly(int column) throws SQLException {
      return this.t2_rsmd.isReadOnly(column);
   }

   public boolean isWritable(int column) throws SQLException {
      return this.t2_rsmd.isWritable(column);
   }

   public boolean isDefinitelyWritable(int column) throws SQLException {
      return this.t2_rsmd.isDefinitelyWritable(column);
   }

   public String getColumnClassName(int column) throws SQLException {
      return this.t2_rsmd.getColumnClassName(column);
   }

   public Object unwrap(Class iface) throws SQLException {
      return iface.isInstance(this.t2_rsmd) ? iface.cast(this.t2_rsmd) : this.t2_rsmd.unwrap(iface);
   }

   public boolean isWrapperFor(Class iface) throws SQLException {
      return this.t2_rsmd != null ? this.t2_rsmd.isWrapperFor(iface) : false;
   }
}
