package org.apache.openjpa.jdbc.sql;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.apache.openjpa.jdbc.kernel.JDBCFetchConfiguration;
import org.apache.openjpa.jdbc.kernel.exps.FilterValue;
import org.apache.openjpa.jdbc.schema.Column;
import org.apache.openjpa.jdbc.schema.ForeignKey;
import org.apache.openjpa.jdbc.schema.Index;
import org.apache.openjpa.jdbc.schema.PrimaryKey;
import org.apache.openjpa.jdbc.schema.Sequence;
import org.apache.openjpa.jdbc.schema.Table;
import org.apache.openjpa.kernel.StoreContext;
import org.apache.openjpa.lib.jdbc.DelegatingDatabaseMetaData;
import org.apache.openjpa.lib.jdbc.DelegatingPreparedStatement;
import org.apache.openjpa.lib.util.Localizer;
import org.apache.openjpa.util.StoreException;
import serp.util.Numbers;

public class OracleDictionary extends DBDictionary {
   public static final String SELECT_HINT = "openjpa.hint.OracleSelectHint";
   public static final String VENDOR_ORACLE = "oracle";
   private static final int BEHAVE_OTHER = 0;
   private static final int BEHAVE_ORACLE = 1;
   private static final int BEHAVE_DATADIRECT31 = 2;
   private static Blob EMPTY_BLOB = null;
   private static Clob EMPTY_CLOB = null;
   private static final Localizer _loc = Localizer.forPackage(OracleDictionary.class);
   public boolean useTriggersForAutoAssign = false;
   public String autoAssignSequenceName = null;
   public boolean openjpa3GeneratedKeyNames = false;
   public boolean useSetFormOfUseForUnicode = true;
   private boolean _checkedUpdateBug = false;
   private boolean _warnedCharColumn = false;
   private boolean _warnedNcharColumn = false;
   private int _driverBehavior = -1;
   private Method _putBytes = null;
   private Method _putString = null;
   private Method _putChars = null;
   private int defaultBatchLimit = 100;

   public OracleDictionary() {
      this.platform = "Oracle";
      this.validationSQL = "SELECT SYSDATE FROM DUAL";
      this.nextSequenceQuery = "SELECT {0}.NEXTVAL FROM DUAL";
      this.stringLengthFunction = "LENGTH({0})";
      this.joinSyntax = 2;
      this.maxTableNameLength = 30;
      this.maxColumnNameLength = 30;
      this.maxIndexNameLength = 30;
      this.maxConstraintNameLength = 30;
      this.maxEmbeddedBlobSize = 4000;
      this.maxEmbeddedClobSize = 4000;
      this.inClauseLimit = 1000;
      this.supportsDeferredConstraints = true;
      this.supportsLockingWithDistinctClause = false;
      this.supportsSelectStartIndex = true;
      this.supportsSelectEndIndex = true;
      this.systemSchemaSet.addAll(Arrays.asList("CTXSYS", "MDSYS", "SYS", "SYSTEM", "WKSYS", "WMSYS", "XDB"));
      this.supportsXMLColumn = true;
      this.xmlTypeName = "XMLType";
      this.bigintTypeName = "NUMBER{0}";
      this.bitTypeName = "NUMBER{0}";
      this.decimalTypeName = "NUMBER{0}";
      this.doubleTypeName = "NUMBER{0}";
      this.integerTypeName = "NUMBER{0}";
      this.numericTypeName = "NUMBER{0}";
      this.smallintTypeName = "NUMBER{0}";
      this.tinyintTypeName = "NUMBER{0}";
      this.longVarcharTypeName = "LONG";
      this.binaryTypeName = "BLOB";
      this.varbinaryTypeName = "BLOB";
      this.longVarbinaryTypeName = "BLOB";
      this.timeTypeName = "DATE";
      this.varcharTypeName = "VARCHAR2{0}";
      this.fixedSizeTypeNameSet.addAll(Arrays.asList("LONG RAW", "RAW", "LONG", "REF"));
      this.reservedWordSet.addAll(Arrays.asList("ACCESS", "AUDIT", "CLUSTER", "COMMENT", "COMPRESS", "EXCLUSIVE", "FILE", "IDENTIFIED", "INCREMENT", "INDEX", "INITIAL", "LOCK", "LONG", "MAXEXTENTS", "MINUS", "MODE", "NOAUDIT", "NOCOMPRESS", "NOWAIT", "OFFLINE", "ONLINE", "PCTFREE", "ROW"));
      this.substringFunctionName = "SUBSTR";
      super.setBatchLimit(this.defaultBatchLimit);
      this.selectWordSet.add("WITH");
   }

   public void endConfiguration() {
      super.endConfiguration();
      if (this.useTriggersForAutoAssign) {
         this.supportsAutoAssign = true;
      }

   }

   public void connectedConfiguration(Connection conn) throws SQLException {
      super.connectedConfiguration(conn);
      if (this.driverVendor == null) {
         DatabaseMetaData meta = conn.getMetaData();
         String url = meta.getURL() == null ? "" : meta.getURL();
         String driverName = meta.getDriverName();
         String metadataClassName;
         if (meta instanceof DelegatingDatabaseMetaData) {
            metadataClassName = ((DelegatingDatabaseMetaData)meta).getInnermostDelegate().getClass().getName();
         } else {
            metadataClassName = meta.getClass().getName();
         }

         if (!metadataClassName.startsWith("oracle.") && url.indexOf("jdbc:oracle:") == -1 && !"Oracle JDBC driver".equals(driverName)) {
            if (!metadataClassName.startsWith("com.ddtek.") && url.indexOf("jdbc:datadirect:oracle:") == -1 && !"Oracle".equals(driverName)) {
               this.driverVendor = "other";
            } else {
               this.driverVendor = "datadirect" + meta.getDriverMajorVersion() + meta.getDriverMinorVersion();
            }
         } else {
            this.driverVendor = "oracle" + meta.getDriverMajorVersion() + meta.getDriverMinorVersion();
            String productVersion = meta.getDatabaseProductVersion().split("Release ", 0)[1].split("\\.", 0)[0];
            int release = Integer.parseInt(productVersion);
            if (release <= 8) {
               if (this.joinSyntax == 0 && this.log.isWarnEnabled()) {
                  this.log.warn(_loc.get("oracle-syntax"));
               }

               this.joinSyntax = 2;
               this.dateTypeName = "DATE";
               this.timestampTypeName = "DATE";
               this.supportsXMLColumn = false;
            }

            this.getStringVal = ".getStringVal()";
         }
      }

      this.cacheDriverBehavior(this.driverVendor);
   }

   private void cacheDriverBehavior(String driverVendor) {
      if (this._driverBehavior == -1) {
         driverVendor = driverVendor.toLowerCase();
         if (driverVendor.startsWith("oracle")) {
            this._driverBehavior = 1;
         } else if (!driverVendor.equals("datadirect30") && !driverVendor.equals("datadirect31")) {
            this._driverBehavior = 0;
         } else {
            this._driverBehavior = 2;
         }

      }
   }

   public void ensureDriverVendor() {
      if (this.driverVendor != null) {
         this.cacheDriverBehavior(this.driverVendor);
      } else {
         if (this.log.isInfoEnabled()) {
            this.log.info(_loc.get("oracle-connecting-for-driver"));
         }

         Connection conn = null;

         try {
            conn = this.conf.getDataSource2((StoreContext)null).getConnection();
            this.connectedConfiguration(conn);
         } catch (SQLException var10) {
            throw SQLExceptions.getStore(var10, (DBDictionary)this);
         } finally {
            if (conn != null) {
               try {
                  conn.close();
               } catch (SQLException var9) {
               }
            }

         }

      }
   }

   public boolean supportsLocking(Select sel) {
      if (!super.supportsLocking(sel)) {
         return false;
      } else {
         return !this.requiresSubselectForRange(sel.getStartIndex(), sel.getEndIndex(), sel.isDistinct(), sel.getOrdering());
      }
   }

   protected SQLBuffer getSelects(Select sel, boolean distinctIdentifiers, boolean forUpdate) {
      if (!this.requiresSubselectForRange(sel.getStartIndex(), sel.getEndIndex(), sel.isDistinct(), sel.getOrdering())) {
         return super.getSelects(sel, distinctIdentifiers, forUpdate);
      } else if (sel.getFromSelect() == null && sel.getTableAliases().size() >= 2) {
         SQLBuffer selectSQL = new SQLBuffer(this);
         List aliases;
         if (distinctIdentifiers) {
            aliases = sel.getIdentifierAliases();
         } else {
            aliases = sel.getSelectAliases();
         }

         int i = 0;

         for(Iterator itr = aliases.iterator(); itr.hasNext(); ++i) {
            Object alias = itr.next();
            if (alias instanceof SQLBuffer) {
               selectSQL.append((SQLBuffer)alias);
            } else {
               selectSQL.append(alias.toString());
            }

            selectSQL.append(" AS c").append(String.valueOf(i));
            if (itr.hasNext()) {
               selectSQL.append(", ");
            }
         }

         return selectSQL;
      } else {
         return super.getSelects(sel, distinctIdentifiers, forUpdate);
      }
   }

   public boolean canOuterJoin(int syntax, ForeignKey fk) {
      if (!super.canOuterJoin(syntax, fk)) {
         return false;
      } else {
         if (fk != null && syntax == 2) {
            if (fk.getConstants().length > 0) {
               return false;
            }

            if (fk.getPrimaryKeyConstants().length > 0) {
               return false;
            }
         }

         return true;
      }
   }

   public SQLBuffer toNativeJoin(Join join) {
      if (join.getType() != 1) {
         return this.toTraditionalJoin(join);
      } else {
         ForeignKey fk = join.getForeignKey();
         if (fk == null) {
            return null;
         } else {
            boolean inverse = join.isForeignKeyInversed();
            Column[] from = inverse ? fk.getPrimaryKeyColumns() : fk.getColumns();
            Column[] to = inverse ? fk.getColumns() : fk.getPrimaryKeyColumns();
            SQLBuffer buf = new SQLBuffer(this);
            int count = 0;

            for(int i = 0; i < from.length; ++count) {
               if (count > 0) {
                  buf.append(" AND ");
               }

               buf.append(join.getAlias1()).append(".").append(from[i]);
               buf.append(" = ");
               buf.append(join.getAlias2()).append(".").append(to[i]);
               buf.append("(+)");
               ++i;
            }

            if (fk.getConstantColumns().length > 0) {
               throw (new StoreException(_loc.get("oracle-constant", join.getTable1(), join.getTable2()))).setFatal(true);
            } else if (fk.getConstantPrimaryKeyColumns().length > 0) {
               throw (new StoreException(_loc.get("oracle-constant", join.getTable1(), join.getTable2()))).setFatal(true);
            } else {
               return buf;
            }
         }
      }
   }

   protected SQLBuffer toSelect(SQLBuffer select, JDBCFetchConfiguration fetch, SQLBuffer tables, SQLBuffer where, SQLBuffer group, SQLBuffer having, SQLBuffer order, boolean distinct, boolean forUpdate, long start, long end, boolean subselect, Select sel) {
      return this.toSelect(select, fetch, tables, where, group, having, order, distinct, forUpdate, start, end, sel);
   }

   protected SQLBuffer toSelect(SQLBuffer select, JDBCFetchConfiguration fetch, SQLBuffer tables, SQLBuffer where, SQLBuffer group, SQLBuffer having, SQLBuffer order, boolean distinct, boolean forUpdate, long start, long end, Select sel) {
      if (!this._checkedUpdateBug) {
         this.ensureDriverVendor();
         if (forUpdate && this._driverBehavior == 2) {
            this.log.warn(_loc.get("dd-lock-bug"));
         }

         this._checkedUpdateBug = true;
      }

      if (start == 0L && end == Long.MAX_VALUE) {
         return super.toSelect(select, fetch, tables, where, group, having, order, distinct, forUpdate, 0L, Long.MAX_VALUE, sel);
      } else {
         SQLBuffer buf = new SQLBuffer(this);
         if (!this.requiresSubselectForRange(start, end, distinct, order)) {
            if (where != null && !where.isEmpty()) {
               buf.append(where).append(" AND ");
            }

            buf.append("ROWNUM <= ").appendValue(end);
            return super.toSelect(select, fetch, tables, buf, group, having, order, distinct, forUpdate, 0L, Long.MAX_VALUE, sel);
         } else {
            SQLBuffer newsel = super.toSelect(select, fetch, tables, where, group, having, order, distinct, forUpdate, 0L, Long.MAX_VALUE, sel);
            if (start == 0L) {
               buf.append(this.getSelectOperation(fetch) + " * FROM (");
               buf.append(newsel);
               buf.append(") WHERE ROWNUM <= ").appendValue(end);
               return buf;
            } else {
               buf.append(this.getSelectOperation(fetch) + " * FROM (SELECT r.*, ROWNUM RNUM FROM (");
               buf.append(newsel);
               buf.append(") r");
               if (end != Long.MAX_VALUE) {
                  buf.append(" WHERE ROWNUM <= ").appendValue(end);
               }

               buf.append(") WHERE RNUM > ").appendValue(start);
               return buf;
            }
         }
      }
   }

   private boolean requiresSubselectForRange(long start, long end, boolean distinct, SQLBuffer order) {
      if (start == 0L && end == Long.MAX_VALUE) {
         return false;
      } else {
         return start != 0L || distinct || order != null && !order.isEmpty();
      }
   }

   public String getSelectOperation(JDBCFetchConfiguration fetch) {
      Object hint = fetch == null ? null : fetch.getHint("openjpa.hint.OracleSelectHint");
      String select = "SELECT";
      if (hint != null) {
         select = select + " " + hint;
      }

      return select;
   }

   public void setString(PreparedStatement stmnt, int idx, String val, Column col) throws SQLException {
      String typeName = col == null ? null : col.getTypeName();
      PreparedStatement inner;
      if (this.useSetFormOfUseForUnicode && typeName != null && (typeName.toLowerCase().startsWith("nvarchar") || typeName.toLowerCase().startsWith("nchar") || typeName.toLowerCase().startsWith("nclob"))) {
         inner = stmnt;
         if (stmnt instanceof DelegatingPreparedStatement) {
            inner = ((DelegatingPreparedStatement)stmnt).getInnermostDelegate();
         }

         if (isOraclePreparedStatement(inner)) {
            try {
               inner.getClass().getMethod("setFormOfUse", Integer.TYPE, Short.TYPE).invoke(inner, new Integer(idx), Class.forName("oracle.jdbc.OraclePreparedStatement").getField("FORM_NCHAR").get((Object)null));
            } catch (Exception var8) {
               this.log.warn(var8);
            }
         } else if (!this._warnedNcharColumn && this.log.isWarnEnabled()) {
            this._warnedNcharColumn = true;
            this.log.warn(_loc.get("unconfigured-nchar-cols"));
         }
      }

      if (col != null && col.getType() == 1 && val != null && val.length() != col.getSize()) {
         inner = stmnt;
         if (stmnt instanceof DelegatingPreparedStatement) {
            inner = ((DelegatingPreparedStatement)stmnt).getInnermostDelegate();
         }

         if (isOraclePreparedStatement(inner)) {
            try {
               inner.getClass().getMethod("setFixedCHAR", Integer.TYPE, String.class).invoke(inner, new Integer(idx), val);
               return;
            } catch (Exception var9) {
               this.log.warn(var9);
            }
         }

         if (!this._warnedCharColumn && this.log.isWarnEnabled()) {
            this._warnedCharColumn = true;
            this.log.warn(_loc.get("unpadded-char-cols"));
         }
      }

      super.setString(stmnt, idx, val, col);
   }

   public void setNull(PreparedStatement stmnt, int idx, int colType, Column col) throws SQLException {
      if (colType == 2004 && this._driverBehavior == 1) {
         stmnt.setBlob(idx, this.getEmptyBlob());
      } else if (colType == 2005 && this._driverBehavior == 1 && !col.isXML()) {
         stmnt.setClob(idx, this.getEmptyClob());
      } else if ((colType == 2002 || colType == 1111) && col != null && col.getTypeName() != null) {
         stmnt.setNull(idx, 2002, col.getTypeName());
      } else if (colType == 91) {
         super.setNull(stmnt, idx, 93, col);
      } else if (colType != 1111 && !col.isXML()) {
         super.setNull(stmnt, idx, colType, col);
      } else {
         super.setNull(stmnt, idx, 0, col);
      }

   }

   public String getClobString(ResultSet rs, int column) throws SQLException {
      if (this._driverBehavior != 1) {
         return super.getClobString(rs, column);
      } else {
         Clob clob = this.getClob(rs, column);
         if (clob == null) {
            return null;
         } else {
            if (clob.getClass().getName().equals("oracle.sql.CLOB")) {
               try {
                  if ((Boolean)Class.forName("oracle.sql.CLOB").getMethod("isEmptyLob").invoke(clob)) {
                     return null;
                  }
               } catch (Exception var5) {
               }
            }

            return clob.length() == 0L ? null : clob.getSubString(1L, (int)clob.length());
         }
      }
   }

   public Timestamp getTimestamp(ResultSet rs, int column, Calendar cal) throws SQLException {
      if (cal == null) {
         try {
            return super.getTimestamp(rs, column, cal);
         } catch (ArrayIndexOutOfBoundsException var5) {
            this.log.warn(_loc.get("oracle-timestamp-bug"), var5);
            throw var5;
         }
      } else {
         Timestamp ts = rs.getTimestamp(column, cal);
         if (ts != null && ts.getNanos() == 0) {
            ts.setNanos(rs.getTimestamp(column).getNanos());
         }

         return ts;
      }
   }

   public Object getObject(ResultSet rs, int column, Map map) throws SQLException {
      Object obj = super.getObject(rs, column, map);
      if (obj == null) {
         return null;
      } else {
         if ("oracle.sql.DATE".equals(obj.getClass().getName())) {
            obj = convertFromOracleType(obj, "dateValue");
         } else if ("oracle.sql.TIMESTAMP".equals(obj.getClass().getName())) {
            obj = convertFromOracleType(obj, "timestampValue");
         }

         return obj;
      }
   }

   private static Object convertFromOracleType(Object obj, String convertMethod) throws SQLException {
      try {
         Method m = obj.getClass().getMethod(convertMethod, (Class[])null);
         return m.invoke(obj, (Object[])null);
      } catch (Throwable var3) {
         Throwable t = var3;
         if (var3 instanceof InvocationTargetException) {
            t = ((InvocationTargetException)var3).getTargetException();
         }

         if (t instanceof SQLException) {
            throw (SQLException)t;
         } else {
            throw new SQLException(t.getMessage());
         }
      }
   }

   public Column[] getColumns(DatabaseMetaData meta, String catalog, String schemaName, String tableName, String columnName, Connection conn) throws SQLException {
      Column[] cols = super.getColumns(meta, catalog, schemaName, tableName, columnName, conn);

      for(int i = 0; cols != null && i < cols.length; ++i) {
         if (cols[i].getTypeName() != null) {
            if (cols[i].getTypeName().toUpperCase().startsWith("TIMESTAMP")) {
               cols[i].setType(93);
            } else if ("BLOB".equalsIgnoreCase(cols[i].getTypeName())) {
               cols[i].setType(2004);
            } else if (!"CLOB".equalsIgnoreCase(cols[i].getTypeName()) && !"NCLOB".equalsIgnoreCase(cols[i].getTypeName())) {
               if ("FLOAT".equalsIgnoreCase(cols[i].getTypeName())) {
                  cols[i].setType(6);
               } else if ("NVARCHAR".equalsIgnoreCase(cols[i].getTypeName())) {
                  cols[i].setType(12);
               } else if ("NCHAR".equalsIgnoreCase(cols[i].getTypeName())) {
                  cols[i].setType(1);
               }
            } else {
               cols[i].setType(2005);
            }
         }
      }

      return cols;
   }

   public PrimaryKey[] getPrimaryKeys(DatabaseMetaData meta, String catalog, String schemaName, String tableName, Connection conn) throws SQLException {
      StringBuffer buf = new StringBuffer();
      buf.append("SELECT t0.OWNER AS TABLE_SCHEM, ").append("t0.TABLE_NAME AS TABLE_NAME, ").append("t0.COLUMN_NAME AS COLUMN_NAME, ").append("t0.CONSTRAINT_NAME AS PK_NAME ").append("FROM ALL_CONS_COLUMNS t0, ALL_CONSTRAINTS t1 ").append("WHERE t0.OWNER = t1.OWNER ").append("AND t0.CONSTRAINT_NAME = t1.CONSTRAINT_NAME ").append("AND t1.CONSTRAINT_TYPE = 'P'");
      if (schemaName != null) {
         buf.append(" AND t0.OWNER = ?");
      }

      if (tableName != null) {
         buf.append(" AND t0.TABLE_NAME = ?");
      }

      PreparedStatement stmnt = conn.prepareStatement(buf.toString());
      ResultSet rs = null;

      try {
         int idx = 1;
         if (schemaName != null) {
            this.setString(stmnt, idx++, schemaName.toUpperCase(), (Column)null);
         }

         if (tableName != null) {
            this.setString(stmnt, idx++, tableName.toUpperCase(), (Column)null);
         }

         rs = stmnt.executeQuery();
         List pkList = new ArrayList();

         while(rs != null && rs.next()) {
            pkList.add(this.newPrimaryKey(rs));
         }

         PrimaryKey[] var11 = (PrimaryKey[])((PrimaryKey[])pkList.toArray(new PrimaryKey[pkList.size()]));
         return var11;
      } finally {
         if (rs != null) {
            try {
               rs.close();
            } catch (Exception var21) {
            }
         }

         try {
            stmnt.close();
         } catch (Exception var20) {
         }

      }
   }

   public Index[] getIndexInfo(DatabaseMetaData meta, String catalog, String schemaName, String tableName, boolean unique, boolean approx, Connection conn) throws SQLException {
      StringBuffer buf = new StringBuffer();
      buf.append("SELECT t0.INDEX_OWNER AS TABLE_SCHEM, ").append("t0.TABLE_NAME AS TABLE_NAME, ").append("DECODE(t1.UNIQUENESS, 'UNIQUE', 0, 'NONUNIQUE', 1) ").append("AS NON_UNIQUE, ").append("t0.INDEX_NAME AS INDEX_NAME, ").append("t0.COLUMN_NAME AS COLUMN_NAME ").append("FROM ALL_IND_COLUMNS t0, ALL_INDEXES t1 ").append("WHERE t0.INDEX_OWNER = t1.OWNER ").append("AND t0.INDEX_NAME = t1.INDEX_NAME");
      if (schemaName != null) {
         buf.append(" AND t0.TABLE_OWNER = ?");
      }

      if (tableName != null) {
         buf.append(" AND t0.TABLE_NAME = ?");
      }

      PreparedStatement stmnt = conn.prepareStatement(buf.toString());
      ResultSet rs = null;

      try {
         int idx = 1;
         if (schemaName != null) {
            this.setString(stmnt, idx++, schemaName.toUpperCase(), (Column)null);
         }

         if (tableName != null) {
            this.setString(stmnt, idx++, tableName.toUpperCase(), (Column)null);
         }

         rs = stmnt.executeQuery();
         List idxList = new ArrayList();

         while(rs != null && rs.next()) {
            idxList.add(this.newIndex(rs));
         }

         Index[] var13 = (Index[])((Index[])idxList.toArray(new Index[idxList.size()]));
         return var13;
      } finally {
         if (rs != null) {
            try {
               rs.close();
            } catch (Exception var23) {
            }
         }

         try {
            stmnt.close();
         } catch (Exception var22) {
         }

      }
   }

   public ForeignKey[] getImportedKeys(DatabaseMetaData meta, String catalog, String schemaName, String tableName, Connection conn) throws SQLException {
      StringBuffer delAction = (new StringBuffer("DECODE(t1.DELETE_RULE")).append(", 'NO ACTION', ").append(3).append(", 'RESTRICT', ").append(1).append(", 'CASCADE', ").append(0).append(", 'SET NULL', ").append(2).append(", 'SET DEFAULT', ").append(4).append(")");
      StringBuffer buf = new StringBuffer();
      buf.append("SELECT t2.OWNER AS PKTABLE_SCHEM, ").append("t2.TABLE_NAME AS PKTABLE_NAME, ").append("t2.COLUMN_NAME AS PKCOLUMN_NAME, ").append("t0.OWNER AS FKTABLE_SCHEM, ").append("t0.TABLE_NAME AS FKTABLE_NAME, ").append("t0.COLUMN_NAME AS FKCOLUMN_NAME, ").append("t0.POSITION AS KEY_SEQ, ").append(delAction).append(" AS DELETE_RULE, ").append("t0.CONSTRAINT_NAME AS FK_NAME, ").append("DECODE(t1.DEFERRED, 'DEFERRED', ").append(5).append(", 'IMMEDIATE', ").append(6).append(") AS DEFERRABILITY ").append("FROM ALL_CONS_COLUMNS t0, ALL_CONSTRAINTS t1, ").append("ALL_CONS_COLUMNS t2 ").append("WHERE t0.OWNER = t1.OWNER ").append("AND t0.CONSTRAINT_NAME = t1.CONSTRAINT_NAME ").append("AND t1.CONSTRAINT_TYPE = 'R' ").append("AND t1.R_OWNER = t2.OWNER ").append("AND t1.R_CONSTRAINT_NAME = t2.CONSTRAINT_NAME ").append("AND t0.POSITION = t2.POSITION");
      if (schemaName != null) {
         buf.append(" AND t0.OWNER = ?");
      }

      if (tableName != null) {
         buf.append(" AND t0.TABLE_NAME = ?");
      }

      buf.append(" ORDER BY t2.OWNER, t2.TABLE_NAME, t0.POSITION");
      PreparedStatement stmnt = conn.prepareStatement(buf.toString());
      ResultSet rs = null;

      try {
         int idx = 1;
         if (schemaName != null) {
            this.setString(stmnt, idx++, schemaName.toUpperCase(), (Column)null);
         }

         if (tableName != null) {
            this.setString(stmnt, idx++, tableName.toUpperCase(), (Column)null);
         }

         rs = stmnt.executeQuery();
         List fkList = new ArrayList();

         while(rs != null && rs.next()) {
            fkList.add(this.newForeignKey(rs));
         }

         ForeignKey[] var12 = (ForeignKey[])((ForeignKey[])fkList.toArray(new ForeignKey[fkList.size()]));
         return var12;
      } finally {
         if (rs != null) {
            try {
               rs.close();
            } catch (Exception var22) {
            }
         }

         try {
            stmnt.close();
         } catch (Exception var21) {
         }

      }
   }

   public String[] getCreateTableSQL(Table table) {
      String[] create = super.getCreateTableSQL(table);
      if (!this.useTriggersForAutoAssign) {
         return create;
      } else {
         Column[] cols = table.getColumns();
         List seqs = null;

         for(int i = 0; cols != null && i < cols.length; ++i) {
            if (cols[i].isAutoAssigned()) {
               if (seqs == null) {
                  seqs = new ArrayList(4);
               }

               String seq = this.autoAssignSequenceName;
               if (seq == null) {
                  if (this.openjpa3GeneratedKeyNames) {
                     seq = this.getOpenJPA3GeneratedKeySequenceName(cols[i]);
                  } else {
                     seq = this.getGeneratedKeySequenceName(cols[i]);
                  }

                  seqs.add("CREATE SEQUENCE " + seq + " START WITH 1");
               }

               String trig;
               if (this.openjpa3GeneratedKeyNames) {
                  trig = this.getOpenJPA3GeneratedKeyTriggerName(cols[i]);
               } else {
                  trig = this.getGeneratedKeyTriggerName(cols[i]);
               }

               seqs.add("CREATE OR REPLACE TRIGGER " + trig + " BEFORE INSERT ON " + table.getName() + " FOR EACH ROW BEGIN SELECT " + seq + ".nextval INTO " + ":new." + cols[i].getName() + " FROM DUAL; " + "END " + trig + ";");
            }
         }

         if (seqs == null) {
            return create;
         } else {
            String[] sql = new String[create.length + seqs.size()];
            System.arraycopy(create, 0, sql, 0, create.length);

            for(int i = 0; i < seqs.size(); ++i) {
               sql[create.length + i] = (String)seqs.get(i);
            }

            return sql;
         }
      }
   }

   public String[] getCreateSequenceSQL(Sequence seq) {
      String[] sql = super.getCreateSequenceSQL(seq);
      if (seq.getAllocate() > 1) {
         sql[0] = sql[0] + " CACHE " + seq.getAllocate();
      }

      return sql;
   }

   protected String getSequencesSQL(String schemaName, String sequenceName) {
      StringBuffer buf = new StringBuffer();
      buf.append("SELECT SEQUENCE_OWNER AS SEQUENCE_SCHEMA, ").append("SEQUENCE_NAME FROM ALL_SEQUENCES");
      if (schemaName != null || sequenceName != null) {
         buf.append(" WHERE ");
      }

      if (schemaName != null) {
         buf.append("SEQUENCE_OWNER = ?");
         if (sequenceName != null) {
            buf.append(" AND ");
         }
      }

      if (sequenceName != null) {
         buf.append("SEQUENCE_NAME = ?");
      }

      return buf.toString();
   }

   public boolean isSystemSequence(String name, String schema, boolean targetSchema) {
      if (super.isSystemSequence(name, schema, targetSchema)) {
         return true;
      } else {
         return this.autoAssignSequenceName != null && name.equalsIgnoreCase(this.autoAssignSequenceName) || this.autoAssignSequenceName == null && name.toUpperCase().startsWith("ST_");
      }
   }

   public Object getGeneratedKey(Column col, Connection conn) throws SQLException {
      if (!this.useTriggersForAutoAssign) {
         return Numbers.valueOf(0L);
      } else {
         String seq = this.autoAssignSequenceName;
         if (seq == null && this.openjpa3GeneratedKeyNames) {
            seq = this.getOpenJPA3GeneratedKeySequenceName(col);
         } else if (seq == null) {
            seq = this.getGeneratedKeySequenceName(col);
         }

         PreparedStatement stmnt = conn.prepareStatement("SELECT " + seq + ".currval FROM DUAL");
         ResultSet rs = null;

         Long var6;
         try {
            rs = stmnt.executeQuery();
            rs.next();
            var6 = Numbers.valueOf(rs.getLong(1));
         } finally {
            if (rs != null) {
               try {
                  rs.close();
               } catch (SQLException var16) {
               }
            }

            try {
               stmnt.close();
            } catch (SQLException var15) {
            }

         }

         return var6;
      }
   }

   protected String getGeneratedKeyTriggerName(Column col) {
      String seqName = this.getGeneratedKeySequenceName(col);
      return seqName.substring(0, seqName.length() - 3) + "TRG";
   }

   protected String getOpenJPA3GeneratedKeySequenceName(Column col) {
      Table table = col.getTable();
      return this.makeNameValid("SEQ_" + table.getName(), table.getSchema().getSchemaGroup(), this.maxTableNameLength, 0);
   }

   protected String getOpenJPA3GeneratedKeyTriggerName(Column col) {
      Table table = col.getTable();
      return this.makeNameValid("TRIG_" + table.getName(), table.getSchema().getSchemaGroup(), this.maxTableNameLength, 0);
   }

   public void putBytes(Object blob, byte[] data) throws SQLException {
      if (blob != null) {
         if (this._putBytes == null) {
            try {
               this._putBytes = blob.getClass().getMethod("putBytes", Long.TYPE, byte[].class);
            } catch (Exception var4) {
               throw new StoreException(var4);
            }
         }

         invokePutLobMethod(this._putBytes, blob, data);
      }
   }

   public void putString(Object clob, String data) throws SQLException {
      if (this._putString == null) {
         try {
            this._putString = clob.getClass().getMethod("putString", Long.TYPE, String.class);
         } catch (Exception var4) {
            throw new StoreException(var4);
         }
      }

      invokePutLobMethod(this._putString, clob, data);
   }

   public void putChars(Object clob, char[] data) throws SQLException {
      if (this._putChars == null) {
         try {
            this._putChars = clob.getClass().getMethod("putChars", Long.TYPE, char[].class);
         } catch (Exception var4) {
            throw new StoreException(var4);
         }
      }

      invokePutLobMethod(this._putChars, clob, data);
   }

   private static void invokePutLobMethod(Method method, Object target, Object data) throws SQLException {
      try {
         method.invoke(target, Numbers.valueOf(1L), data);
      } catch (InvocationTargetException var5) {
         Throwable t = var5.getTargetException();
         if (t instanceof SQLException) {
            throw (SQLException)t;
         } else {
            throw new StoreException(t);
         }
      } catch (Exception var6) {
         throw new StoreException(var6);
      }
   }

   private Clob getEmptyClob() throws SQLException {
      if (EMPTY_CLOB != null) {
         return EMPTY_CLOB;
      } else {
         try {
            return EMPTY_CLOB = (Clob)Class.forName("oracle.sql.CLOB", true, Thread.currentThread().getContextClassLoader()).getMethod("empty_lob").invoke((Object)null);
         } catch (Exception var2) {
            throw new SQLException(var2.getMessage());
         }
      }
   }

   private Blob getEmptyBlob() throws SQLException {
      if (EMPTY_BLOB != null) {
         return EMPTY_BLOB;
      } else {
         try {
            return EMPTY_BLOB = (Blob)Class.forName("oracle.sql.BLOB", true, Thread.currentThread().getContextClassLoader()).getMethod("empty_lob").invoke((Object)null);
         } catch (Exception var2) {
            throw new SQLException(var2.getMessage());
         }
      }
   }

   private static boolean isOraclePreparedStatement(Statement stmnt) {
      try {
         return Class.forName("oracle.jdbc.OraclePreparedStatement").isInstance(stmnt);
      } catch (Exception var2) {
         return false;
      }
   }

   public void appendXmlComparison(SQLBuffer buf, String op, FilterValue lhs, FilterValue rhs, boolean lhsxml, boolean rhsxml) {
      super.appendXmlComparison(buf, op, lhs, rhs, lhsxml, rhsxml);
      if (lhsxml && rhsxml) {
         this.appendXmlComparison2(buf, op, lhs, rhs);
      } else if (lhsxml) {
         this.appendXmlComparison1(buf, op, lhs, rhs);
      } else {
         this.appendXmlComparison1(buf, op, rhs, lhs);
      }

   }

   private void appendXmlComparison1(SQLBuffer buf, String op, FilterValue lhs, FilterValue rhs) {
      this.appendXmlExtractValue(buf, lhs);
      buf.append(" ").append(op).append(" ");
      rhs.appendTo(buf);
   }

   private void appendXmlComparison2(SQLBuffer buf, String op, FilterValue lhs, FilterValue rhs) {
      this.appendXmlExtractValue(buf, lhs);
      buf.append(" ").append(op).append(" ");
      this.appendXmlExtractValue(buf, rhs);
   }

   private void appendXmlExtractValue(SQLBuffer buf, FilterValue val) {
      buf.append("extractValue(").append(val.getColumnAlias(val.getFieldMapping().getColumns()[0])).append(",'/*/");
      val.appendTo(buf);
      buf.append("')");
   }

   public void insertBlobForStreamingLoad(Row row, Column col, Object ob) throws SQLException {
      if (ob == null) {
         col.setType(1111);
      }

      row.setNull(col);
   }

   public void insertClobForStreamingLoad(Row row, Column col, Object ob) throws SQLException {
      if (ob == null) {
         col.setType(1111);
      }

      row.setNull(col);
   }
}
