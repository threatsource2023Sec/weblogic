package weblogic.jdbc.rowset;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.sql.DatabaseMetaData;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.StringTokenizer;
import weblogic.xml.stream.Attribute;
import weblogic.xml.stream.ElementFactory;
import weblogic.xml.stream.StartElement;
import weblogic.xml.stream.XMLInputStream;
import weblogic.xml.stream.XMLName;
import weblogic.xml.stream.XMLOutputStream;

public final class CachedRowSetMetaData implements WLRowSetMetaData, Serializable, XMLSchemaConstants, Cloneable {
   private static final long serialVersionUID = -343025277802741983L;
   private static final boolean DEBUG = true;
   private static final boolean VERBOSE = false;
   private int columnCount = 0;
   private boolean haveSetPKColumns = false;
   private int version = 2;
   private DatabaseMetaDataHolder databaseMetaData = null;
   private boolean isValidMetaData = false;
   private String writeTableName;
   private String rowName;
   private String rowSetName;
   private boolean isReadOnly = false;
   private String defaultNamespace = "http://www.openuri.org";
   private ColumnAttributes[] columnAttributes = null;
   private transient Map rowAttributes = new HashMap();
   private int optimisticPolicy = 1;
   private boolean batchInserts;
   private boolean batchDeletes;
   private boolean batchUpdates;
   private boolean groupDeletes;
   private int groupDeleteSize = 50;
   private int batchVerifySize = 50;
   private boolean verboseSQL;
   private String schemaLocation;
   ArrayList matchColumns = new ArrayList();

   public boolean equals(Object obj) {
      if (obj == null) {
         return false;
      } else if (obj == this) {
         return true;
      } else if (!(obj instanceof CachedRowSetMetaData)) {
         return false;
      } else {
         CachedRowSetMetaData rs = (CachedRowSetMetaData)obj;
         if (rs.columnCount != this.columnCount) {
            return false;
         } else if (rs.haveSetPKColumns != this.haveSetPKColumns) {
            return false;
         } else if (rs.isValidMetaData != this.isValidMetaData) {
            return false;
         } else if (rs.databaseMetaData == this.databaseMetaData || rs.databaseMetaData != null && rs.databaseMetaData.equals(this.databaseMetaData)) {
            if (rs.writeTableName != this.writeTableName && !rs.writeTableName.equals(this.writeTableName)) {
               return false;
            } else if (rs.rowName != this.rowName && !rs.rowName.equals(this.rowName)) {
               return false;
            } else if (rs.rowSetName != this.rowSetName && !rs.rowSetName.equals(this.rowSetName)) {
               return false;
            } else if (rs.isReadOnly != this.isReadOnly) {
               return false;
            } else if (rs.defaultNamespace != this.defaultNamespace && !rs.defaultNamespace.equals(this.defaultNamespace)) {
               return false;
            } else {
               if (rs.columnAttributes != this.columnAttributes) {
                  if (rs.columnAttributes.length != this.columnAttributes.length) {
                     return false;
                  }

                  for(int i = 0; i < this.columnAttributes.length; ++i) {
                     if (!this.columnAttributes[i].equals(rs.columnAttributes[i])) {
                        return false;
                     }
                  }
               }

               if (!rs.rowAttributes.equals(this.rowAttributes)) {
                  return false;
               } else if (rs.optimisticPolicy != this.optimisticPolicy) {
                  return false;
               } else if (rs.batchInserts != this.batchInserts) {
                  return false;
               } else if (rs.batchDeletes != this.batchDeletes) {
                  return false;
               } else if (rs.batchUpdates != this.batchUpdates) {
                  return false;
               } else if (rs.groupDeletes != this.groupDeletes) {
                  return false;
               } else if (rs.groupDeleteSize != this.groupDeleteSize) {
                  return false;
               } else if (rs.batchVerifySize != this.batchVerifySize) {
                  return false;
               } else if (rs.verboseSQL != this.verboseSQL) {
                  return false;
               } else {
                  return rs.schemaLocation == this.schemaLocation || rs.schemaLocation.equals(this.schemaLocation);
               }
            }
         } else {
            return false;
         }
      }
   }

   public int hashCode() {
      return 0;
   }

   public CachedRowSetMetaData() throws SQLException {
   }

   protected Object clone() {
      CachedRowSetMetaData ret = null;

      try {
         ret = (CachedRowSetMetaData)super.clone();
      } catch (Throwable var3) {
         return null;
      }

      if (this.columnAttributes == null) {
         ret.columnAttributes = null;
      } else {
         ret.columnAttributes = new ColumnAttributes[this.columnAttributes.length];

         for(int i = 0; i < this.columnAttributes.length; ++i) {
            if (this.columnAttributes[i] != null) {
               ret.columnAttributes[i] = (ColumnAttributes)((ColumnAttributes)this.columnAttributes[i].clone());
            }
         }
      }

      if (this.rowAttributes == null) {
         ret.rowAttributes = null;
      } else {
         ret.rowAttributes = (Map)((HashMap)this.rowAttributes).clone();
      }

      return ret;
   }

   public void initialize(ResultSetMetaData metaData, DatabaseMetaData dbmd) throws SQLException {
      this.columnCount = metaData.getColumnCount();
      this.columnAttributes = new ColumnAttributes[this.columnCount];

      for(int i = 0; i < this.columnCount; ++i) {
         this.columnAttributes[i] = new ColumnAttributes();
         this.columnAttributes[i].colName = metaData.getColumnName(i + 1);

         try {
            this.columnAttributes[i].tableName = metaData.getTableName(i + 1);
         } catch (Throwable var11) {
            this.columnAttributes[i].tableName = "";
         }

         try {
            this.columnAttributes[i].catalogName = metaData.getCatalogName(i + 1);
         } catch (Throwable var10) {
            this.columnAttributes[i].catalogName = "";
         }

         this.columnAttributes[i].columnLabel = metaData.getColumnLabel(i + 1);

         try {
            this.columnAttributes[i].schemaName = metaData.getSchemaName(i + 1);
         } catch (Throwable var9) {
            this.columnAttributes[i].schemaName = "";
         }

         this.columnAttributes[i].isAutoIncrement = metaData.isAutoIncrement(i + 1);
         this.columnAttributes[i].isCaseSensitive = metaData.isCaseSensitive(i + 1);
         this.columnAttributes[i].isCurrency = metaData.isCurrency(i + 1);
         this.columnAttributes[i].isNullable = metaData.isNullable(i + 1);

         try {
            this.columnAttributes[i].isReadOnly = metaData.isReadOnly(i + 1);
         } catch (Exception var8) {
            this.columnAttributes[i].isReadOnly = false;
         }

         this.columnAttributes[i].isSearchable = metaData.isSearchable(i + 1);
         this.columnAttributes[i].isSigned = metaData.isSigned(i + 1);

         try {
            this.columnAttributes[i].isWritable = metaData.isWritable(i + 1);
         } catch (Exception var7) {
            this.columnAttributes[i].isWritable = true;
         }

         this.columnAttributes[i].columnDisplaySize = metaData.getColumnDisplaySize(i + 1);

         try {
            this.columnAttributes[i].isDefinitelyWritable = metaData.isDefinitelyWritable(i + 1);
         } catch (Exception var6) {
            this.columnAttributes[i].isDefinitelyWritable = false;
         }

         try {
            this.columnAttributes[i].columnClassName = metaData.getColumnClassName(i + 1);
         } catch (SQLException var5) {
            this.columnAttributes[i].columnClassName = "";
         }

         this.columnAttributes[i].columnTypeName = metaData.getColumnTypeName(i + 1);
         this.columnAttributes[i].columnType = metaData.getColumnType(i + 1);
         if (this.columnAttributes[i].columnType == -1 && this.columnAttributes[i].columnTypeName.equals("xml")) {
            this.columnAttributes[i].columnType = 2009;
         }

         if (this.columnAttributes[i].columnType == 93 && "DATE".equals(this.columnAttributes[i].columnTypeName)) {
            this.columnAttributes[i].columnType = 91;
         }

         if (this.isNumericType(this.columnAttributes[i].columnType)) {
            this.columnAttributes[i].precision = metaData.getPrecision(i + 1);
            this.columnAttributes[i].scale = metaData.getScale(i + 1);
         }
      }

      if (dbmd == null) {
         this.databaseMetaData = null;
         this.isValidMetaData = false;
      } else {
         this.databaseMetaData = new DatabaseMetaDataHolder(dbmd);
         this.isValidMetaData = true;
      }

   }

   public void setVersion(int version) {
      this.version = version;
   }

   public int getVersion() {
      return this.version;
   }

   public void setMetaDataHolder(DatabaseMetaDataHolder holder) {
      this.databaseMetaData = holder;
      this.isValidMetaData = holder != null;
   }

   public DatabaseMetaDataHolder getMetaDataHolder() {
      return this.databaseMetaData;
   }

   public boolean isValidMetaData() {
      return this.isValidMetaData;
   }

   public void addColumns(ResultSetMetaData metaData) throws SQLException {
      int[] columns = new int[metaData.getColumnCount()];

      for(int i = 0; i < metaData.getColumnCount(); ++i) {
         columns[i] = i + 1;
      }

      this.addColumns(metaData, columns);
   }

   public void addColumns(ResultSetMetaData metaData, int[] columns) throws SQLException {
      int newColumnCount = columns.length + this.columnCount;
      ColumnAttributes[] newColumnAttributes = new ColumnAttributes[newColumnCount];
      System.arraycopy(this.columnAttributes, 0, newColumnAttributes, 0, this.columnAttributes.length);
      this.columnAttributes = newColumnAttributes;
      int j = 0;

      for(int i = this.columnCount; i < newColumnCount; ++i) {
         this.columnAttributes[i] = new ColumnAttributes();
         this.columnAttributes[i].colName = metaData.getColumnName(columns[j]);

         try {
            this.columnAttributes[i].tableName = metaData.getTableName(columns[j]);
         } catch (Throwable var11) {
            this.columnAttributes[i].tableName = "";
         }

         try {
            this.columnAttributes[i].catalogName = metaData.getCatalogName(columns[j]);
         } catch (Throwable var10) {
            this.columnAttributes[i].catalogName = "";
         }

         this.columnAttributes[i].columnLabel = metaData.getColumnLabel(columns[j]);

         try {
            this.columnAttributes[i].schemaName = metaData.getSchemaName(columns[j]);
         } catch (Throwable var9) {
            this.columnAttributes[i].schemaName = "";
         }

         this.columnAttributes[i].isAutoIncrement = metaData.isAutoIncrement(columns[j]);
         this.columnAttributes[i].isCaseSensitive = metaData.isCaseSensitive(columns[j]);
         this.columnAttributes[i].isCurrency = metaData.isCurrency(columns[j]);
         this.columnAttributes[i].isNullable = metaData.isNullable(columns[j]);
         this.columnAttributes[i].isReadOnly = metaData.isReadOnly(columns[j]);
         this.columnAttributes[i].isSearchable = metaData.isSearchable(columns[j]);
         this.columnAttributes[i].isSigned = metaData.isSigned(columns[j]);
         this.columnAttributes[i].isWritable = metaData.isWritable(columns[j]);
         this.columnAttributes[i].columnDisplaySize = metaData.getColumnDisplaySize(columns[j]);
         this.columnAttributes[i].isDefinitelyWritable = metaData.isDefinitelyWritable(columns[j]);

         try {
            this.columnAttributes[i].columnClassName = metaData.getColumnClassName(columns[j]);
         } catch (SQLException var8) {
            this.columnAttributes[i].columnClassName = "";
         }

         this.columnAttributes[i].columnTypeName = metaData.getColumnTypeName(columns[j]);
         this.columnAttributes[i].columnType = metaData.getColumnType(columns[j]);
         if (this.columnAttributes[i].columnType == 93 && "DATE".equals(this.columnAttributes[i].columnTypeName)) {
            this.columnAttributes[i].columnType = 91;
         }

         if (this.isNumericType(this.columnAttributes[i].columnType)) {
            this.columnAttributes[i].precision = metaData.getPrecision(columns[j]);
            this.columnAttributes[i].scale = metaData.getScale(columns[j]);
         }

         ++j;
      }

      this.columnCount = newColumnCount;
   }

   private boolean isNumericType(int type) {
      return type == -7 || type == 5 || type == 4 || type == -5 || type == 6 || type == 7 || type == 2 || type == 3;
   }

   public String toString() {
      StringBuffer sb = new StringBuffer();
      sb.append("[CachedRowSetMetaData] [" + System.identityHashCode(this) + "] ");
      sb.append("columnCount: " + this.columnCount + "\n");

      for(int i = 0; i < this.columnCount; ++i) {
         sb.append(this.columnAttributes[i].toString());
         sb.append('\n');
      }

      return sb.toString();
   }

   public int getOptimisticPolicy() {
      return this.optimisticPolicy;
   }

   public void setBatchInserts(boolean b) {
      this.batchInserts = b;
   }

   public boolean getBatchInserts() {
      return this.batchInserts;
   }

   public void setBatchDeletes(boolean b) {
      this.batchDeletes = b;
   }

   public boolean getBatchDeletes() {
      return this.batchDeletes;
   }

   public void setBatchUpdates(boolean b) {
      this.batchUpdates = b;
   }

   public boolean getBatchUpdates() {
      return this.batchUpdates;
   }

   public void setGroupDeletes(boolean b) {
      this.groupDeletes = b;
   }

   public boolean getGroupDeletes() {
      return this.groupDeletes;
   }

   public void setGroupDeleteSize(int size) throws SQLException {
      if (size <= 0) {
         throw new SQLException("setGroupDeleteSize must be called  with a size > 0");
      } else {
         this.groupDeleteSize = size;
      }
   }

   public int getGroupDeleteSize() {
      return this.groupDeleteSize;
   }

   public void setBatchVerifySize(int size) throws SQLException {
      if (size <= 0) {
         throw new SQLException("setBatchVerifySize must be called  with a size > 0");
      } else {
         this.batchVerifySize = size;
      }
   }

   public int getBatchVerifySize() {
      return this.batchVerifySize;
   }

   public void setOptimisticPolicyAsString(String s) throws SQLException {
      if (!"VERIFY_READ_COLUMNS".equalsIgnoreCase(s) && !"".equals(s)) {
         if ("VERIFY_MODIFIED_COLUMNS".equalsIgnoreCase(s)) {
            this.setOptimisticPolicy(2);
         } else if ("VERIFY_SELECTED_COLUMNS".equalsIgnoreCase(s)) {
            this.setOptimisticPolicy(3);
         } else if ("VERIFY_NONE".equalsIgnoreCase(s)) {
            this.setOptimisticPolicy(4);
         } else if ("VERIFY_AUTO_VERSION_COLUMNS".equalsIgnoreCase(s)) {
            this.setOptimisticPolicy(5);
         } else {
            if (!"VERIFY_VERSION_COLUMNS".equalsIgnoreCase(s)) {
               throw new SQLException("Unexpected parameter to setOptimisticPolicyAsString: " + s + ".  The parameter must be VERIFY_MODIFIED_COLUMNS, VERIFY_READ_COLUMNS, VERIFY_SELECTED_COLUMNS, VERIFY_NONE, VERIFY_AUTO_VERSION_COLUMNS, VERIFY_VERSION_COLUMNS");
            }

            this.setOptimisticPolicy(6);
         }
      } else {
         this.setOptimisticPolicy(1);
      }

   }

   public void setOptimisticPolicy(int p) throws SQLException {
      switch (p) {
         case 1:
         case 2:
         case 3:
         case 4:
         case 5:
         case 6:
            this.optimisticPolicy = p;
            return;
         default:
            throw new SQLException("Unexpected parameter to setOptimisticPolicy: " + p + ".  The parameter must be VERIFY_MODIFIED_COLUMNS, VERIFY_READ_COLUMNS, VERIFY_SELECTED_COLUMNS, VERIFY_NONE, VERIFY_AUTO_VERSION_COLUMNS, VERIFY_VERSION_COLUMNS");
      }
   }

   public String getOptimisticPolicyAsString() {
      switch (this.getOptimisticPolicy()) {
         case 1:
            return "VERIFY_READ_COLUMNS";
         case 2:
            return "VERIFY_MODIFIED_COLUMNS";
         case 3:
            return "VERIFY_SELECTED_COLUMNS";
         case 4:
            return "VERIFY_NONE";
         case 5:
            return "VERIFY_AUTO_VERSION_COLUMNS";
         case 6:
            return "VERIFY_VERSION_COLUMNS";
         default:
            throw new AssertionError("Unexpected getOptimisticPolicy:" + this.getOptimisticPolicy());
      }
   }

   public void setVerboseSQL(boolean b) {
      this.verboseSQL = b;
   }

   public boolean getVerboseSQL() {
      return this.verboseSQL;
   }

   boolean claimSchema(String name) {
      return this.rowSetName == null ? true : this.rowSetName.equals(name);
   }

   void setXMLAttributes(int i, List l) {
      this.columnAttributes[i].setXMLAttributes(l);
   }

   void readXMLAttributes(int i, StartElement se) throws IOException {
      this.columnAttributes[i].readXMLAttributes(se);
   }

   public int findColumn(String n) throws SQLException {
      if (n != null) {
         for(int i = 0; i < this.columnCount; ++i) {
            if (n.equalsIgnoreCase(this.columnAttributes[i].colName)) {
               return i + 1;
            }
         }
      }

      throw new SQLException("There is no column named: " + n + " in this RowSet.");
   }

   private void checkColumn(int i) throws SQLException {
      if (this.columnCount == 0) {
         throw new SQLException("You should populate the RowSet with data or call setColumnCount before calling any other method onthe RowSetMetaData");
      } else if (i == 0) {
         throw new SQLException("You have specified a column index of 0.  JDBC indexes begin with 1.");
      } else if (i < 1 || i > this.columnCount) {
         throw new SQLException("There is no column: " + i + " in this RowSet.");
      }
   }

   public int getColumnCount() {
      return this.columnCount;
   }

   void setColumnCountInternal(int count) throws SQLException {
      if (count < 1) {
         throw new SQLException("Invalid column count: " + count);
      } else {
         this.columnCount = count;
         this.columnAttributes = new ColumnAttributes[this.columnCount];

         for(int i = 0; i < this.columnCount; ++i) {
            this.columnAttributes[i] = new ColumnAttributes();
         }

      }
   }

   public void setColumnCount(int count) throws SQLException {
      if (this.columnCount != 0) {
         throw new SQLException("You cannot reset the columnCount of the RowSetMetaData to a new value.");
      } else {
         this.setColumnCountInternal(count);
      }
   }

   public void setColumnName(int i, String n) throws SQLException {
      this.checkColumn(i);
      this.columnAttributes[i - 1].colName = n;
   }

   public String getColumnName(int i) throws SQLException {
      this.checkColumn(i);
      return this.columnAttributes[i - 1].colName;
   }

   String getQualifiedColumnName(int i) throws SQLException {
      this.checkColumn(i);
      return this.columnAttributes[i - 1].getQualifiedTableName() + "." + this.columnAttributes[i - 1].colName;
   }

   public void setTableName(String n) throws SQLException {
      this.checkColumn(1);
      String catalogName = "";
      String schemaName = "";
      String tableName = "";
      if (n != null) {
         if (this.isValidMetaData) {
            TableNameParser parser = new TableNameParser(n, this.databaseMetaData);
            String[] parts = parser.parse();
            catalogName = parts[0];
            schemaName = parts[1];
            tableName = parts[2];
         } else {
            for(StringTokenizer st = new StringTokenizer(n, "."); st.hasMoreTokens(); tableName = st.nextToken()) {
               catalogName = schemaName;
               schemaName = tableName;
            }
         }
      }

      for(int i = 0; i < this.columnAttributes.length; ++i) {
         this.columnAttributes[i].setTableName(catalogName, schemaName, tableName);
      }

   }

   public void setTableName(int i, String n) throws SQLException {
      this.checkColumn(i);
      this.columnAttributes[i - 1].setTableName(n);
   }

   public void setTableName(String colName, String n) throws SQLException {
      this.setTableName(this.findColumn(colName), n);
   }

   public String getTableName(int i) throws SQLException {
      this.checkColumn(i);
      return this.columnAttributes[i - 1].tableName;
   }

   public String getQualifiedTableName(int i) throws SQLException {
      this.checkColumn(i);
      return this.columnAttributes[i - 1].getQualifiedTableName();
   }

   public String getQualifiedTableName(String n) throws SQLException {
      return this.getQualifiedTableName(this.findColumn(n));
   }

   public void setMatchColumns(int[] cols) throws SQLException {
      for(int i = 0; i < cols.length; ++i) {
         this.setMatchColumn(cols[i], true);
      }

   }

   public int[] getMatchColumns() throws SQLException {
      int[] ret = new int[this.matchColumns.size()];

      for(int i = 0; i < ret.length; ++i) {
         ret[i] = (Integer)this.matchColumns.get(i);
      }

      return ret;
   }

   public void setMatchColumn(int i, boolean b) throws SQLException {
      this.checkColumn(i);
      int index = this.matchColumns.indexOf(new Integer(i));
      if (b) {
         if (index == -1) {
            this.matchColumns.add(new Integer(i));
         }
      } else if (index != -1) {
         this.matchColumns.remove(index);
      }

   }

   public void setKeyColumns(int[] keys) throws SQLException {
      int i;
      for(i = 0; i < this.columnCount; ++i) {
         this.columnAttributes[i].isPrimaryKeyColumn = false;
      }

      for(i = 0; i < keys.length; ++i) {
         this.setPrimaryKeyColumn(keys[i], true);
      }

   }

   public int[] getKeyColumns() throws SQLException {
      int[] keys = new int[this.columnCount];
      int j = 0;

      for(int i = 0; i < this.columnCount; ++i) {
         if (this.columnAttributes[i].isPrimaryKeyColumn) {
            keys[j++] = i + 1;
         }
      }

      int[] ret = new int[j];

      for(int i = 0; i < j; ++i) {
         ret[i] = keys[i];
      }

      return ret;
   }

   public void setPrimaryKeyColumn(String n, boolean b) throws SQLException {
      this.setPrimaryKeyColumn(this.findColumn(n), b);
   }

   public void setPrimaryKeyColumn(int i, boolean b) throws SQLException {
      this.checkColumn(i);
      if (b) {
         this.haveSetPKColumns = true;
         this.columnAttributes[i - 1].isReadOnly = true;
      }

      this.columnAttributes[i - 1].isPrimaryKeyColumn = b;
   }

   public boolean isPrimaryKeyColumn(int i) throws SQLException {
      this.checkColumn(i);
      return this.columnAttributes[i - 1].isPrimaryKeyColumn;
   }

   public boolean isPrimaryKeyColumn(String n) throws SQLException {
      return this.isPrimaryKeyColumn(this.findColumn(n));
   }

   public boolean haveSetPKColumns() {
      if (this.haveSetPKColumns) {
         return true;
      } else {
         for(int i = 0; i < this.columnCount; ++i) {
            if (this.columnAttributes[i].isPrimaryKeyColumn) {
               this.haveSetPKColumns = true;
               return true;
            }
         }

         return false;
      }
   }

   public void setAutoIncrement(int i, boolean b) throws SQLException {
      this.checkColumn(i);
      this.columnAttributes[i - 1].isAutoIncrement = b;
      this.setReadOnly(i, b);
   }

   public boolean isAutoIncrement(int i) throws SQLException {
      this.checkColumn(i);
      return this.columnAttributes[i - 1].isAutoIncrement;
   }

   public void setCaseSensitive(int i, boolean b) throws SQLException {
      this.checkColumn(i);
      this.columnAttributes[i - 1].isCaseSensitive = b;
   }

   public boolean isCaseSensitive(int i) throws SQLException {
      this.checkColumn(i);
      return this.columnAttributes[i - 1].isCaseSensitive;
   }

   public void setSearchable(int i, boolean b) throws SQLException {
      this.checkColumn(i);
      this.columnAttributes[i - 1].isSearchable = b;
   }

   public boolean isSearchable(int i) throws SQLException {
      this.checkColumn(i);
      return this.columnAttributes[i - 1].isSearchable;
   }

   public void setCurrency(int i, boolean b) throws SQLException {
      this.checkColumn(i);
      this.columnAttributes[i - 1].isCurrency = b;
   }

   public boolean isCurrency(int i) throws SQLException {
      this.checkColumn(i);
      return this.columnAttributes[i - 1].isCurrency;
   }

   public void setNullable(int i, int x) throws SQLException {
      this.checkColumn(i);
      this.columnAttributes[i - 1].isNullable = x;
   }

   public int isNullable(int i) throws SQLException {
      this.checkColumn(i);
      return this.columnAttributes[i - 1].isNullable;
   }

   public void setSigned(int i, boolean b) throws SQLException {
      this.checkColumn(i);
      this.columnAttributes[i - 1].isSigned = b;
   }

   public boolean isSigned(int i) throws SQLException {
      this.checkColumn(i);
      return this.columnAttributes[i - 1].isSigned;
   }

   public String getCatalogName(int i) throws SQLException {
      this.checkColumn(i);
      return this.columnAttributes[i - 1].catalogName;
   }

   public void setCatalogName(int i, String n) throws SQLException {
      this.checkColumn(i);
      if (n != null) {
         this.columnAttributes[i - 1].catalogName = n;
      } else {
         this.columnAttributes[i - 1].catalogName = "";
      }

   }

   public int getColumnDisplaySize(int i) throws SQLException {
      this.checkColumn(i);
      return this.columnAttributes[i - 1].columnDisplaySize;
   }

   public void setColumnDisplaySize(int i, int x) throws SQLException {
      this.checkColumn(i);
      this.columnAttributes[i - 1].columnDisplaySize = x;
   }

   public String getColumnLabel(int i) throws SQLException {
      this.checkColumn(i);
      return this.columnAttributes[i - 1].columnLabel;
   }

   public void setColumnLabel(int i, String n) throws SQLException {
      this.checkColumn(i);
      this.columnAttributes[i - 1].columnLabel = n;
   }

   public String getSchemaName(int i) throws SQLException {
      this.checkColumn(i);
      return this.columnAttributes[i - 1].schemaName;
   }

   public void setSchemaName(int i, String n) throws SQLException {
      this.checkColumn(i);
      this.columnAttributes[i - 1].schemaName = n;
   }

   public int getColumnType(int i) throws SQLException {
      this.checkColumn(i);
      return this.columnAttributes[i - 1].columnType;
   }

   public void setColumnType(int i, int x) throws SQLException {
      this.checkColumn(i);
      this.columnAttributes[i - 1].columnType = x;
   }

   public String getColumnTypeName(int i) throws SQLException {
      this.checkColumn(i);
      return this.columnAttributes[i - 1].columnTypeName;
   }

   public void setColumnTypeName(int i, String n) throws SQLException {
      this.checkColumn(i);
      this.columnAttributes[i - 1].columnTypeName = n;
   }

   public int getPrecision(int i) throws SQLException {
      this.checkColumn(i);
      return this.columnAttributes[i - 1].precision;
   }

   public void setPrecision(int i, int x) throws SQLException {
      this.checkColumn(i);
      this.columnAttributes[i - 1].precision = x;
   }

   public int getScale(int i) throws SQLException {
      this.checkColumn(i);
      return this.columnAttributes[i - 1].scale;
   }

   public void setScale(int i, int x) throws SQLException {
      this.checkColumn(i);
      this.columnAttributes[i - 1].scale = x;
   }

   public boolean isReadOnly() {
      return this.isReadOnly;
   }

   public void setReadOnly(boolean b) {
      this.isReadOnly = b;

      for(int i = 0; i < this.columnCount; ++i) {
         this.columnAttributes[i].isReadOnly = b;
      }

   }

   public boolean isReadOnly(int i) throws SQLException {
      this.checkColumn(i);
      return this.isReadOnly ? true : this.columnAttributes[i - 1].isReadOnly;
   }

   public boolean isReadOnly(String colName) throws SQLException {
      return this.isReadOnly(this.findColumn(colName));
   }

   public void setReadOnly(String n, boolean b) throws SQLException {
      this.setReadOnly(this.findColumn(n), b);
   }

   public void setReadOnly(int i, boolean b) throws SQLException {
      this.checkColumn(i);
      this.columnAttributes[i - 1].isReadOnly = b;
   }

   public void setVerifySelectedColumn(int i, boolean b) throws SQLException {
      this.checkColumn(i);
      this.columnAttributes[i - 1].isSelectedColumn = b;
   }

   boolean hasSelectedColumn() throws SQLException {
      for(int i = 0; i < this.columnAttributes.length; ++i) {
         if (this.columnAttributes[i].isSelectedColumn) {
            return true;
         }
      }

      return false;
   }

   public boolean isSelectedColumn(int i) throws SQLException {
      this.checkColumn(i);
      return this.columnAttributes[i - 1].isSelectedColumn;
   }

   public void setVerifySelectedColumn(String colName, boolean b) throws SQLException {
      this.setVerifySelectedColumn(this.findColumn(colName), b);
   }

   public boolean isSelectedColumn(String colName) throws SQLException {
      return this.isSelectedColumn(this.findColumn(colName));
   }

   public void setAutoVersionColumn(int i, boolean b) throws SQLException {
      this.checkColumn(i);
      this.columnAttributes[i - 1].isAutoVersionColumn = b;
   }

   public boolean isAutoVersionColumn(int i) throws SQLException {
      this.checkColumn(i);
      return this.columnAttributes[i - 1].isAutoVersionColumn;
   }

   public void setAutoVersionColumn(String colName, boolean b) throws SQLException {
      this.setAutoVersionColumn(this.findColumn(colName), b);
   }

   public boolean isAutoVersionColumn(String colName) throws SQLException {
      return this.isAutoVersionColumn(this.findColumn(colName));
   }

   public void setVersionColumn(int i, boolean b) throws SQLException {
      this.checkColumn(i);
      this.columnAttributes[i - 1].isVersionColumn = b;
   }

   public boolean isVersionColumn(int i) throws SQLException {
      this.checkColumn(i);
      return this.columnAttributes[i - 1].isVersionColumn;
   }

   public void setVersionColumn(String colName, boolean b) throws SQLException {
      this.setVersionColumn(this.findColumn(colName), b);
   }

   public boolean isVersionColumn(String colName) throws SQLException {
      return this.isVersionColumn(this.findColumn(colName));
   }

   public boolean isWritable(int i) throws SQLException {
      this.checkColumn(i);
      return this.columnAttributes[i - 1].isWritable;
   }

   public boolean isDefinitelyWritable(int i) throws SQLException {
      this.checkColumn(i);
      return this.columnAttributes[i - 1].isDefinitelyWritable;
   }

   public void setDefinitelyWritable(int i, boolean b) throws SQLException {
      this.checkColumn(i);
      this.columnAttributes[i - 1].isDefinitelyWritable = b;
   }

   public String getColumnClassName(int i) throws SQLException {
      this.checkColumn(i);
      return this.columnAttributes[i - 1].columnClassName;
   }

   public void setColumnClassName(int i, String s) throws SQLException {
      this.checkColumn(i);
      this.columnAttributes[i - 1].columnClassName = s;
   }

   public String getWriteColumnName(int i) throws SQLException {
      this.checkColumn(i);
      return this.columnAttributes[i - 1].writeColumnName != null ? this.columnAttributes[i - 1].writeColumnName : this.columnAttributes[i - 1].colName;
   }

   public String getWriteColumnName(String colName) throws SQLException {
      return this.getWriteColumnName(this.findColumn(colName));
   }

   public void setWriteColumnName(int i, String writeColumnName) throws SQLException {
      this.checkColumn(i);
      this.columnAttributes[i - 1].writeColumnName = writeColumnName;
   }

   public void setWriteColumnName(String colName, String writeColumnName) throws SQLException {
      this.setWriteColumnName(this.findColumn(colName), writeColumnName);
   }

   public String getWriteTableName() {
      return this.writeTableName;
   }

   private boolean seq(String a, String b) {
      if (a == null) {
         return b == null;
      } else {
         return a.equals(b);
      }
   }

   public void setWriteTableName(String n) throws SQLException {
      this.writeTableName = n;
      if (n != null) {
         if (this.isValidMetaData) {
            TableNameParser parser = new TableNameParser(this.writeTableName, this.databaseMetaData);
            String[] parts = parser.parse();

            for(int i = 0; i < this.columnCount; ++i) {
               ColumnAttributes c = this.columnAttributes[i];
               if (!parser.identifierEqual(c.tableName, parts[2]) || !parser.identifierEqual(c.schemaName, parts[1]) || !parser.identifierEqual(c.catalogName, parts[0])) {
                  c.isReadOnly = true;
               }
            }
         } else {
            String writeCatalog = "";
            String writeSchema = "";
            String writeTable = "";

            for(StringTokenizer st = new StringTokenizer(n, "."); st.hasMoreTokens(); writeTable = st.nextToken()) {
               writeCatalog = writeSchema;
               writeSchema = writeTable;
            }

            for(int i = 0; i < this.columnCount; ++i) {
               ColumnAttributes c = this.columnAttributes[i];
               if (!this.seq(c.tableName, writeTable) || !"".equals(writeSchema) && !this.seq(c.schemaName, writeSchema) || !"".equals(writeCatalog) && !this.seq(c.catalogName, writeCatalog)) {
                  c.isReadOnly = true;
               }
            }
         }

      }
   }

   public void markUpdateProperties(String writeTableName, String pkColumn, String rowVersionColumn) throws SQLException {
      this.setTableName(writeTableName);
      this.setWriteTableName(writeTableName);
      this.setPrimaryKeyColumn(this.findColumn(pkColumn), true);
      this.setOptimisticPolicy(6);
      this.setVersionColumn(this.findColumn(rowVersionColumn), true);
   }

   public String getDefaultNamespace() {
      return this.defaultNamespace;
   }

   public void setDefaultNamespace(String defaultNamespace) {
      this.defaultNamespace = defaultNamespace;
   }

   public String getRowName() {
      if (this.rowName == null) {
         return this.writeTableName != null ? this.writeTableName + "Row" : "TableRow";
      } else {
         return this.rowName;
      }
   }

   public void setRowName(String rowName) {
      this.rowName = rowName;
   }

   public String getRowSetName() {
      return this.rowSetName == null ? this.getRowName() + "Set" : this.rowSetName;
   }

   public void setRowSetName(String rowSetName) {
      this.rowSetName = rowSetName;
   }

   public void writeXMLSchema(XMLOutputStream xos) throws IOException, SQLException {
      XMLSchemaWriter writer = new XMLSchemaWriter(this);
      writer.writeSchema(xos);
   }

   public void loadXMLSchema(XMLInputStream xis) throws IOException, SQLException {
      XMLSchemaReader r = new XMLSchemaReader(this);
      r.loadSchema(xis);
   }

   public String getXMLSchemaLocation() {
      return this.schemaLocation == null ? this.getDefaultNamespace() + "/" + this.getRowSetName() + ".xsd" : this.schemaLocation;
   }

   public void setXMLSchemaLocation(String schemaLocation) {
      this.schemaLocation = schemaLocation;
   }

   private XMLName getName(String namespace, String prefix) {
      return ElementFactory.createXMLName(namespace, "", prefix);
   }

   public Properties getRowAttributes(String namespace, String prefix) {
      return (Properties)this.rowAttributes.get(this.getName(namespace, prefix));
   }

   public Map getAllRowAttributes() {
      return this.rowAttributes;
   }

   void readRowAttributes(StartElement se) throws IOException, SQLException {
      this.rowAttributes = XMLUtils.readPropertyMapFromAttributes(se);
   }

   public Properties setRowAttributes(String namespace, String prefix, Properties rowProperties) throws SQLException {
      if (!"http://www.w3.org/2001/XMLSchema".equals(namespace) && !"http://www.bea.com/2002/10/weblogicdata".equals(namespace)) {
         return (Properties)this.rowAttributes.put(this.getName(namespace, prefix), rowProperties);
      } else {
         throw new SQLException("namespace parameter " + namespace + " cannot be reserved namespaces: " + "http://www.bea.com/2002/10/weblogicdata" + " or " + "http://www.w3.org/2001/XMLSchema");
      }
   }

   public Properties getColAttributes(String namespace, String prefix, String colName) throws SQLException {
      return this.getColAttributes(namespace, prefix, this.findColumn(colName));
   }

   public Properties getColAttributes(String namespace, String prefix, int i) throws SQLException {
      this.checkColumn(i);
      Map m = this.columnAttributes[i - 1].attributes;
      return m == null ? null : (Properties)m.get(this.getName(namespace, prefix));
   }

   public Map getAllColAttributes(String colName) throws SQLException {
      return this.getAllColAttributes(this.findColumn(colName));
   }

   public Map getAllColAttributes(int i) throws SQLException {
      this.checkColumn(i);
      return this.columnAttributes[i - 1].attributes == null ? Collections.EMPTY_MAP : this.columnAttributes[i - 1].attributes;
   }

   public Properties setColAttributes(String namespace, String prefix, int i, Properties colProperties) throws SQLException {
      if (!"http://www.w3.org/2001/XMLSchema".equals(namespace) && !"http://www.bea.com/2002/10/weblogicdata".equals(namespace)) {
         this.checkColumn(i);
         Map m = this.columnAttributes[i - 1].attributes;
         if (m == null) {
            m = new HashMap();
            this.columnAttributes[i - 1].attributes = (Map)m;
         }

         return (Properties)((Map)m).put(this.getName(namespace, prefix), colProperties);
      } else {
         throw new SQLException("namespace parameter " + namespace + " cannot be reserved namespaces: " + "http://www.bea.com/2002/10/weblogicdata" + " or " + "http://www.w3.org/2001/XMLSchema");
      }
   }

   public Properties setColAttributes(String namespace, String prefix, String colName, Properties colProperties) throws SQLException {
      return this.setColAttributes(namespace, prefix, this.findColumn(colName), colProperties);
   }

   private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
      this.defaultNamespace = "http://www.openuri.org";
      this.groupDeletes = true;
      this.groupDeleteSize = 50;
      this.batchVerifySize = 50;
      this.rowAttributes = new HashMap();
      in.defaultReadObject();
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

   final class ColumnAttributes implements Serializable, Cloneable {
      private static final long serialVersionUID = -3162667379829885159L;
      String colName;
      String writeColumnName;
      String tableName;
      String catalogName;
      String columnClassName;
      String columnLabel;
      String schemaName;
      String columnTypeName;
      transient Map attributes;
      boolean isAutoIncrement = false;
      boolean isCaseSensitive = false;
      boolean isCurrency = false;
      boolean isDefinitelyWritable = false;
      boolean isPrimaryKeyColumn = false;
      boolean isReadOnly = false;
      boolean isWritable = true;
      boolean isSearchable = true;
      boolean isSigned = false;
      boolean isAutoVersionColumn = false;
      boolean isVersionColumn = false;
      boolean isSelectedColumn = false;
      int isNullable;
      int columnDisplaySize;
      int columnType;
      int precision;
      int scale;

      public boolean equals(Object obj) {
         if (obj == null) {
            return false;
         } else if (obj == this) {
            return true;
         } else {
            ColumnAttributes col = (ColumnAttributes)obj;
            if (col.colName != this.colName) {
               return false;
            } else if (col.writeColumnName != this.writeColumnName) {
               return false;
            } else if (col.tableName != this.tableName) {
               return false;
            } else if (col.catalogName != this.catalogName) {
               return false;
            } else if (col.columnClassName != this.columnClassName) {
               return false;
            } else if (col.columnLabel != this.columnLabel) {
               return false;
            } else if (col.schemaName != this.schemaName) {
               return false;
            } else if (col.columnTypeName != this.columnTypeName) {
               return false;
            } else if (col.attributes != this.attributes) {
               return false;
            } else if (col.isAutoIncrement != this.isAutoIncrement) {
               return false;
            } else if (col.isCaseSensitive != this.isCaseSensitive) {
               return false;
            } else if (col.isCurrency != this.isCurrency) {
               return false;
            } else if (col.isDefinitelyWritable != this.isDefinitelyWritable) {
               return false;
            } else if (col.isPrimaryKeyColumn != this.isPrimaryKeyColumn) {
               return false;
            } else if (col.isReadOnly != this.isReadOnly) {
               return false;
            } else if (col.isWritable != this.isWritable) {
               return false;
            } else if (col.isSearchable != this.isSearchable) {
               return false;
            } else if (col.isSigned != this.isSigned) {
               return false;
            } else if (col.isAutoVersionColumn != this.isAutoVersionColumn) {
               return false;
            } else if (col.isVersionColumn != this.isVersionColumn) {
               return false;
            } else if (col.isSelectedColumn != this.isSelectedColumn) {
               return false;
            } else if (col.isNullable != this.isNullable) {
               return false;
            } else if (col.columnDisplaySize != this.columnDisplaySize) {
               return false;
            } else if (col.columnType != this.columnType) {
               return false;
            } else if (col.precision != this.precision) {
               return false;
            } else {
               return col.scale == this.scale;
            }
         }
      }

      public int hashCode() {
         return 0;
      }

      private Attribute getAttr(XMLName name, String value) {
         return ElementFactory.createAttribute(name, value);
      }

      protected Object clone() {
         ColumnAttributes ret = null;

         try {
            ret = (ColumnAttributes)super.clone();
         } catch (Throwable var3) {
            return null;
         }

         if (this.attributes != null) {
            ret.attributes = (Map)((HashMap)this.attributes).clone();
         }

         return ret;
      }

      public void setXMLAttributes(List l) {
         if (this.isAutoIncrement) {
            l.add(this.getAttr(XMLSchemaConstants.WLDD_AUTO, "true"));
         }

         if (this.isPrimaryKeyColumn) {
            l.add(this.getAttr(XMLSchemaConstants.WLDD_PK, "true"));
         }

         if (this.isReadOnly) {
            l.add(this.getAttr(XMLSchemaConstants.WLDD_READONLY, "true"));
         }

         if (this.isAutoVersionColumn) {
            l.add(this.getAttr(XMLSchemaConstants.WLDD_AUTO_VERSION, "true"));
         }

         if (this.isDefinitelyWritable) {
            l.add(this.getAttr(XMLSchemaConstants.WLDD_DEFINITELY_WRITABLE, "true"));
         }

         if (this.isVersionColumn) {
            l.add(this.getAttr(XMLSchemaConstants.WLDD_VERSION, "true"));
         }

         if (this.isSelectedColumn) {
            l.add(this.getAttr(XMLSchemaConstants.WLDD_SELECTED, "true"));
         }

         if (this.writeColumnName != null) {
            l.add(this.getAttr(XMLSchemaConstants.WLDD_WRITECOL, this.writeColumnName));
         }

         if (this.tableName != null && !"".equals(this.tableName)) {
            l.add(this.getAttr(XMLSchemaConstants.WLDD_TABLE_NAME, this.getQualifiedTableName()));
         }

         if (this.isNullable != 0) {
            l.add(XMLSchemaConstants.NIL_ATTR);
         }

         if (this.attributes != null) {
            XMLUtils.addAttributesFromPropertyMap(l, this.attributes);
         }

      }

      public void readXMLAttributes(StartElement se) throws IOException {
         this.colName = XMLUtils.getRequiredAttribute(se, "name").getValue();
         this.isAutoIncrement = XMLUtils.getOptionalBooleanAttribute(se, XMLSchemaConstants.WLDD_AUTO);
         this.isDefinitelyWritable = XMLUtils.getOptionalBooleanAttribute(se, XMLSchemaConstants.WLDD_DEFINITELY_WRITABLE);
         this.isPrimaryKeyColumn = XMLUtils.getOptionalBooleanAttribute(se, XMLSchemaConstants.WLDD_PK);
         this.isReadOnly = XMLUtils.getOptionalBooleanAttribute(se, XMLSchemaConstants.WLDD_READONLY);
         this.isAutoVersionColumn = XMLUtils.getOptionalBooleanAttribute(se, XMLSchemaConstants.WLDD_AUTO_VERSION);
         this.isVersionColumn = XMLUtils.getOptionalBooleanAttribute(se, XMLSchemaConstants.WLDD_VERSION);
         this.isSelectedColumn = XMLUtils.getOptionalBooleanAttribute(se, XMLSchemaConstants.WLDD_SELECTED);
         this.writeColumnName = XMLUtils.getOptionalStringAttribute(se, XMLSchemaConstants.WLDD_WRITECOL);
         String n = XMLUtils.getOptionalStringAttribute(se, XMLSchemaConstants.WLDD_TABLE_NAME);
         this.setTableName(n);
         String type = XMLUtils.getRequiredAttribute(se, "type").getValue();
         String jdbcType = XMLUtils.getOptionalStringAttribute(se, XMLSchemaConstants.WLDD_JDBC_TYPE);
         if (jdbcType == null) {
            this.columnType = TypeMapper.getDbType(type);
            this.columnTypeName = TypeMapper.getJDBCTypeAsString(this.columnType);
         } else {
            this.columnTypeName = jdbcType;
            this.columnType = TypeMapper.getJDBCTypeFromString(this.columnTypeName);
         }

         if (XMLUtils.getOptionalBooleanAttribute(se, XMLSchemaConstants.NILLABLE_NAME)) {
            this.isNullable = 1;
         } else {
            this.isNullable = 0;
         }

         this.attributes = XMLUtils.readPropertyMapFromAttributes(se);
      }

      String getQualifiedTableName() {
         StringBuffer sb = new StringBuffer();
         if (CachedRowSetMetaData.this.isValidMetaData) {
            sb.append(this.tableName);
            if (CachedRowSetMetaData.this.databaseMetaData.supportsSchemasInDataManipulation() && this.schemaName != null && !"".equals(this.schemaName)) {
               sb.insert(0, ".").insert(0, this.schemaName);
            }

            if (CachedRowSetMetaData.this.databaseMetaData.supportsCatalogsInDataManipulation() && this.catalogName != null && !"".equals(this.catalogName)) {
               String catalogSeparator = CachedRowSetMetaData.this.databaseMetaData.getCatalogSeparator();
               if (catalogSeparator != null && !"".equals(catalogSeparator)) {
                  if (CachedRowSetMetaData.this.databaseMetaData.isCatalogAtStart()) {
                     sb.insert(0, catalogSeparator).insert(0, this.catalogName);
                  } else {
                     sb.append(catalogSeparator).append(this.catalogName);
                  }
               }
            }
         } else {
            if (this.catalogName != null && !"".equals(this.catalogName)) {
               sb.append(this.catalogName).append(".");
            }

            if (this.schemaName != null && !"".equals(this.schemaName)) {
               sb.append(this.schemaName).append(".");
            }

            sb.append(this.tableName);
         }

         return sb.toString();
      }

      void setTableName(String n) {
         String catalogName = "";
         String schemaName = "";
         String tableName = "";
         if (n != null) {
            if (CachedRowSetMetaData.this.isValidMetaData) {
               try {
                  TableNameParser parser = new TableNameParser(n, CachedRowSetMetaData.this.databaseMetaData);
                  String[] parts = parser.parse();
                  catalogName = parts[0];
                  schemaName = parts[1];
                  tableName = parts[2];
               } catch (ParseException var7) {
               }
            } else {
               for(StringTokenizer st = new StringTokenizer(n, "."); st.hasMoreTokens(); tableName = st.nextToken()) {
                  catalogName = schemaName;
                  schemaName = tableName;
               }
            }
         }

         this.setTableName(catalogName, schemaName, tableName);
      }

      void setTableName(String catalogName, String schemaName, String tableName) {
         if (catalogName != null) {
            this.catalogName = catalogName;
         } else {
            this.catalogName = "";
         }

         if (schemaName != null) {
            this.schemaName = schemaName;
         } else {
            this.schemaName = "";
         }

         this.tableName = tableName;
      }

      public String toString() {
         return "colName=" + this.colName + ", tableName=" + this.tableName + ", catalogName=" + this.catalogName + ", columnClassName=" + this.columnClassName + ", columnLabel=" + this.columnLabel + ", schemaName=" + this.schemaName + ", columnTypeName=" + this.columnTypeName + ", isAutoIncrement=" + this.isAutoIncrement + ", isCaseSensitive=" + this.isCaseSensitive + ", isCurrency=" + this.isCurrency + ", isDefinitelyWritable=" + this.isDefinitelyWritable + ", isPrimaryKeyColumn=" + this.isPrimaryKeyColumn + ", isReadOnly=" + this.isReadOnly + ", isWritable=" + this.isWritable + ", isSearchable=" + this.isSearchable + ", isSigned=" + this.isSigned + ", isNullable=" + this.isNullable + ", columnDisplaySize=" + this.columnDisplaySize + ", columnType=" + this.columnType + ", precision=" + this.precision + ", scale=" + this.scale;
      }
   }
}
