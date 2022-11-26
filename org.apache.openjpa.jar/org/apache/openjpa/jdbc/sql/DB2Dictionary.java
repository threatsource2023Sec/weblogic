package org.apache.openjpa.jdbc.sql;

import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.StringTokenizer;
import org.apache.openjpa.jdbc.kernel.JDBCFetchConfiguration;
import org.apache.openjpa.jdbc.kernel.exps.FilterValue;
import org.apache.openjpa.jdbc.kernel.exps.Lit;
import org.apache.openjpa.jdbc.kernel.exps.Param;
import org.apache.openjpa.jdbc.kernel.exps.Val;
import org.apache.openjpa.jdbc.schema.Column;
import org.apache.openjpa.jdbc.schema.Index;
import org.apache.openjpa.jdbc.schema.Schema;
import org.apache.openjpa.jdbc.schema.Sequence;
import org.apache.openjpa.jdbc.schema.Table;
import org.apache.openjpa.kernel.Filters;
import org.apache.openjpa.lib.util.Localizer;
import org.apache.openjpa.meta.JavaTypes;
import org.apache.openjpa.util.OpenJPAException;
import org.apache.openjpa.util.UnsupportedException;
import serp.util.Strings;

public class DB2Dictionary extends AbstractDB2Dictionary {
   private static final Localizer _loc = Localizer.forPackage(DB2Dictionary.class);
   public String optimizeClause = "optimize for";
   public String rowClause = "row";
   protected int db2ServerType = 0;
   public static final int db2ISeriesV5R3OrEarlier = 1;
   public static final int db2UDBV81OrEarlier = 2;
   public static final int db2ZOSV8xOrLater = 3;
   public static final int db2UDBV82OrLater = 4;
   public static final int db2ISeriesV5R4OrLater = 5;
   private static final String forUpdate = "FOR UPDATE";
   private static final String withRSClause = "WITH RS";
   private static final String withRRClause = "WITH RR";
   private static final String useKeepUpdateLockClause = "USE AND KEEP UPDATE LOCKS";
   private static final String useKeepExclusiveLockClause = "USE AND KEEP EXCLUSIVE LOCKS";
   private static final String forReadOnlyClause = "FOR READ ONLY";
   protected String databaseProductName = null;
   protected String databaseProductVersion = null;
   protected int maj = 0;
   protected int min = 0;
   private int defaultBatchLimit = 100;

   public DB2Dictionary() {
      this.platform = "DB2";
      this.validationSQL = "SELECT DISTINCT(CURRENT TIMESTAMP) FROM SYSIBM.SYSTABLES";
      this.supportsSelectEndIndex = true;
      this.nextSequenceQuery = "VALUES NEXTVAL FOR {0}";
      this.sequenceSQL = "SELECT SEQSCHEMA AS SEQUENCE_SCHEMA, SEQNAME AS SEQUENCE_NAME FROM SYSCAT.SEQUENCES";
      this.sequenceSchemaSQL = "SEQSCHEMA = ?";
      this.sequenceNameSQL = "SEQNAME = ?";
      this.characterColumnSize = 254;
      this.binaryTypeName = "BLOB(1M)";
      this.longVarbinaryTypeName = "BLOB(1M)";
      this.varbinaryTypeName = "BLOB(1M)";
      this.clobTypeName = "CLOB(1M)";
      this.longVarcharTypeName = "LONG VARCHAR";
      this.datePrecision = 1000;
      this.storeCharsAsNumbers = false;
      this.fixedSizeTypeNameSet.addAll(Arrays.asList("LONG VARCHAR FOR BIT DATA", "LONG VARCHAR", "LONG VARGRAPHIC"));
      this.systemSchemas = new String("SYSCAT,SYSIBM,SYSSTAT,SYSIBMADM,SYSTOOLS");
      this.maxConstraintNameLength = 18;
      this.maxIndexNameLength = 18;
      this.maxColumnNameLength = 30;
      this.supportsDeferredConstraints = false;
      this.supportsDefaultDeleteAction = false;
      this.supportsAlterTableWithDropColumn = false;
      this.supportsNullTableForGetColumns = false;
      this.requiresCastForMathFunctions = true;
      this.requiresCastForComparisons = true;
      this.reservedWordSet.addAll(Arrays.asList("AFTER", "ALIAS", "ALLOW", "APPLICATION", "ASSOCIATE", "ASUTIME", "AUDIT", "AUX", "AUXILIARY", "BEFORE", "BINARY", "BUFFERPOOL", "CACHE", "CALL", "CALLED", "CAPTURE", "CARDINALITY", "CCSID", "CLUSTER", "COLLECTION", "COLLID", "COMMENT", "CONCAT", "CONDITION", "CONTAINS", "COUNT_BIG", "CURRENT_LC_CTYPE", "CURRENT_PATH", "CURRENT_SERVER", "CURRENT_TIMEZONE", "CYCLE", "DATA", "DATABASE", "DAYS", "DB2GENERAL", "DB2GENRL", "DB2SQL", "DBINFO", "DEFAULTS", "DEFINITION", "DETERMINISTIC", "DISALLOW", "DO", "DSNHATTR", "DSSIZE", "DYNAMIC", "EACH", "EDITPROC", "ELSEIF", "ENCODING", "END-EXEC1", "ERASE", "EXCLUDING", "EXIT", "FENCED", "FIELDPROC", "FILE", "FINAL", "FREE", "FUNCTION", "GENERAL", "GENERATED", "GRAPHIC", "HANDLER", "HOLD", "HOURS", "IF", "INCLUDING", "INCREMENT", "INDEX", "INHERIT", "INOUT", "INTEGRITY", "ISOBID", "ITERATE", "JAR", "JAVA", "LABEL", "LC_CTYPE", "LEAVE", "LINKTYPE", "LOCALE", "LOCATOR", "LOCATORS", "LOCK", "LOCKMAX", "LOCKSIZE", "LONG", "LOOP", "MAXVALUE", "MICROSECOND", "MICROSECONDS", "MINUTES", "MINVALUE", "MODE", "MODIFIES", "MONTHS", "NEW", "NEW_TABLE", "NOCACHE", "NOCYCLE", "NODENAME", "NODENUMBER", "NOMAXVALUE", "NOMINVALUE", "NOORDER", "NULLS", "NUMPARTS", "OBID", "OLD", "OLD_TABLE", "OPTIMIZATION", "OPTIMIZE", "OUT", "OVERRIDING", "PACKAGE", "PARAMETER", "PART", "PARTITION", "PATH", "PIECESIZE", "PLAN", "PRIQTY", "PROGRAM", "PSID", "QUERYNO", "READS", "RECOVERY", "REFERENCING", "RELEASE", "RENAME", "REPEAT", "RESET", "RESIGNAL", "RESTART", "RESULT", "RESULT_SET_LOCATOR", "RETURN", "RETURNS", "ROUTINE", "ROW", "RRN", "RUN", "SAVEPOINT", "SCRATCHPAD", "SECONDS", "SECQTY", "SECURITY", "SENSITIVE", "SIGNAL", "SIMPLE", "SOURCE", "SPECIFIC", "SQLID", "STANDARD", "START", "STATIC", "STAY", "STOGROUP", "STORES", "STYLE", "SUBPAGES", "SYNONYM", "SYSFUN", "SYSIBM", "SYSPROC", "SYSTEM", "TABLESPACE", "TRIGGER", "TYPE", "UNDO", "UNTIL", "VALIDPROC", "VARIABLE", "VARIANT", "VCAT", "VOLUMES", "WHILE", "WLM", "YEARS"));
      super.setBatchLimit(this.defaultBatchLimit);
      this.selectWordSet.add("WITH");
   }

   public boolean supportsRandomAccessResultSet(Select sel, boolean forUpdate) {
      return !forUpdate && super.supportsRandomAccessResultSet(sel, forUpdate);
   }

   protected void appendSelectRange(SQLBuffer buf, long start, long end, boolean subselect) {
      if (!subselect) {
         buf.append(" FETCH FIRST ").append(Long.toString(end)).append(" ROWS ONLY");
      }

   }

   protected void appendSelect(SQLBuffer selectSQL, Object alias, Select sel, int idx) {
      Object val = sel.getSelects().get(idx);
      if (val instanceof Lit) {
         selectSQL.append("CAST(");
      }

      super.appendSelect(selectSQL, alias, sel, idx);
      if (val instanceof Lit) {
         Class c = ((Lit)val).getType();
         int javaTypeCode = JavaTypes.getTypeCode(c);
         int jdbcTypeCode = this.getJDBCType(javaTypeCode, false);
         String typeName = this.getTypeName(jdbcTypeCode);
         selectSQL.append(" AS " + typeName);
         if (String.class.equals(c)) {
            selectSQL.append("(" + this.characterColumnSize + ")");
         }

         selectSQL.append(")");
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
      buf.append(this.sequenceSQL);
      if (schemaName != null || sequenceName != null) {
         buf.append(" WHERE ");
      }

      if (schemaName != null) {
         buf.append(this.sequenceSchemaSQL);
         if (sequenceName != null) {
            buf.append(" AND ");
         }
      }

      if (sequenceName != null) {
         buf.append(this.sequenceNameSQL);
      }

      return buf.toString();
   }

   public Connection decorate(Connection conn) throws SQLException {
      conn = super.decorate(conn);
      if (this.conf.getTransactionIsolationConstant() == -1 && conn.getTransactionIsolation() < 2) {
         conn.setTransactionIsolation(2);
      }

      return conn;
   }

   private boolean isJDBC3(DatabaseMetaData meta) {
      try {
         return meta.getJDBCMajorVersion() >= 3;
      } catch (Throwable var3) {
         return false;
      }
   }

   public void connectedConfiguration(Connection conn) throws SQLException {
      super.connectedConfiguration(conn);
      DatabaseMetaData metaData = conn.getMetaData();
      this.databaseProductName = metaData.getDatabaseProductName();
      this.databaseProductVersion = metaData.getDatabaseProductVersion();
      this.getProductVersionMajorMinorForISeries();
      if (this.maj > 0) {
         if (this.isDB2ISeriesV5R3OrEarlier()) {
            this.db2ServerType = 1;
         } else if (this.isDB2ISeriesV5R4OrLater()) {
            this.db2ServerType = 5;
         }
      }

      if (this.db2ServerType == 0) {
         if (this.isJDBC3(metaData)) {
            this.maj = metaData.getDatabaseMajorVersion();
            this.min = metaData.getDatabaseMinorVersion();
         } else {
            this.getProductVersionMajorMinor();
         }

         if (this.isDB2UDBV81OrEarlier()) {
            this.db2ServerType = 2;
         } else if (this.isDB2ZOSV8xOrLater()) {
            this.db2ServerType = 3;
         } else if (this.isDB2UDBV82OrLater()) {
            this.db2ServerType = 4;
         }
      }

      if (this.db2ServerType != 0 && this.maj != 0) {
         if (this.maj >= 9 || this.maj == 8 && this.min >= 2) {
            this.supportsLockingWithMultipleTables = true;
            this.supportsLockingWithInnerJoin = true;
            this.supportsLockingWithOuterJoin = true;
            this.forUpdateClause = "WITH RR USE AND KEEP UPDATE LOCKS";
            if (this.maj >= 9) {
               this.supportsXMLColumn = true;
            }
         }

         switch (this.db2ServerType) {
            case 1:
            case 5:
               this.lastGeneratedKeyQuery = "SELECT IDENTITY_VAL_LOCAL() FROM SYSIBM.SYSDUMMY1";
               this.nextSequenceQuery = "SELECT NEXTVAL FOR {0} FROM SYSIBM.SYSDUMMY1";
               this.validationSQL = "SELECT DISTINCT(CURRENT TIMESTAMP) FROM QSYS2.SYSTABLES";
               this.sequenceSQL = "SELECT SEQUENCE_SCHEMA, SEQUENCE_NAME FROM QSYS2.SYSSEQUENCES";
               this.sequenceSchemaSQL = "SEQUENCE_SCHEMA = ?";
               this.sequenceNameSQL = "SEQUENCE_NAME = ?";
            case 2:
            case 4:
            default:
               break;
            case 3:
               this.characterColumnSize = 255;
               this.lastGeneratedKeyQuery = "SELECT IDENTITY_VAL_LOCAL() FROM SYSIBM.SYSDUMMY1";
               this.nextSequenceQuery = "SELECT NEXTVAL FOR {0} FROM SYSIBM.SYSDUMMY1";
               this.sequenceSQL = "SELECT SCHEMA AS SEQUENCE_SCHEMA, NAME AS SEQUENCE_NAME FROM SYSIBM.SYSSEQUENCES";
               this.sequenceSchemaSQL = "SCHEMA = ?";
               this.sequenceNameSQL = "NAME = ?";
               if (this.maj == 8) {
                  this.bigintTypeName = "DECIMAL(31,0)";
               }
         }

      } else {
         throw new UnsupportedException(_loc.get("db-not-supported", new Object[]{this.databaseProductName, this.databaseProductVersion}));
      }
   }

   protected String getForUpdateClause(JDBCFetchConfiguration fetch, boolean isForUpdate, Select sel) {
      StringBuffer forUpdateString = new StringBuffer(this.getOptimizeClause(sel));

      try {
         int isolationLevel;
         if (fetch != null && fetch.getIsolation() != -1) {
            isolationLevel = fetch.getIsolation();
         } else {
            isolationLevel = this.conf.getTransactionIsolationConstant();
         }

         if (isForUpdate) {
            switch (this.db2ServerType) {
               case 1:
               case 2:
                  if (isolationLevel == 8) {
                     forUpdateString.append(" ").append(this.forUpdateClause);
                  } else {
                     forUpdateString.append(" ").append("FOR UPDATE").append(" ").append("WITH RS");
                  }
                  break;
               case 3:
               case 4:
                  if (isolationLevel == 8) {
                     forUpdateString.append(" ").append("FOR READ ONLY").append(" ").append("WITH RR").append(" ").append("USE AND KEEP UPDATE LOCKS");
                  } else {
                     forUpdateString.append(" ").append("FOR READ ONLY").append(" ").append("WITH RS").append(" ").append("USE AND KEEP UPDATE LOCKS");
                  }
                  break;
               case 5:
                  if (isolationLevel == 8) {
                     forUpdateString.append(" ").append("FOR READ ONLY").append(" ").append("WITH RR").append(" ").append("USE AND KEEP EXCLUSIVE LOCKS");
                  } else {
                     forUpdateString.append(" ").append("FOR READ ONLY").append(" ").append("WITH RS").append(" ").append("USE AND KEEP EXCLUSIVE LOCKS");
                  }
            }
         }
      } catch (Exception var7) {
         if (this.log.isTraceEnabled()) {
            this.log.error(var7.toString(), var7);
         }
      }

      return forUpdateString.toString();
   }

   public boolean isDB2UDBV82OrLater() {
      boolean match = false;
      if (this.databaseProductName != null && (this.databaseProductVersion.indexOf("SQL") != -1 || this.databaseProductName.indexOf("DB2/") != -1) && (this.maj == 8 && this.min >= 2 || this.maj >= 9)) {
         match = true;
      }

      return match;
   }

   public boolean isDB2ZOSV8xOrLater() {
      boolean match = false;
      if (this.databaseProductName != null && (this.databaseProductVersion.indexOf("DSN") != -1 || this.databaseProductName.indexOf("DB2/") == -1) && this.maj >= 8) {
         match = true;
      }

      return match;
   }

   public boolean isDB2ISeriesV5R3OrEarlier() {
      boolean match = false;
      if (this.databaseProductName != null && this.databaseProductName.indexOf("AS") != -1 && (this.maj == 5 && this.min <= 3 || this.maj < 5)) {
         match = true;
      }

      return match;
   }

   public boolean isDB2ISeriesV5R4OrLater() {
      boolean match = false;
      if (this.databaseProductName != null && this.databaseProductName.indexOf("AS") != -1 && (this.maj >= 6 || this.maj == 5 && this.min >= 4)) {
         match = true;
      }

      return match;
   }

   public boolean isDB2UDBV81OrEarlier() {
      boolean match = false;
      if (this.databaseProductName != null && (this.databaseProductVersion.indexOf("SQL") != -1 || this.databaseProductName.indexOf("DB2/") != -1) && (this.maj == 8 && this.min <= 1 || this.maj < 8)) {
         match = true;
      }

      return match;
   }

   private void getProductVersionMajorMinorForISeries() {
      if (this.databaseProductName.indexOf("AS") != -1) {
         String s = this.databaseProductVersion.substring(this.databaseProductVersion.indexOf(86));
         s = s.toUpperCase();
         StringTokenizer stringtokenizer = new StringTokenizer(s, "VRM", false);
         if (stringtokenizer.countTokens() == 3) {
            String s1 = stringtokenizer.nextToken();
            this.maj = Integer.parseInt(s1);
            String s2 = stringtokenizer.nextToken();
            this.min = Integer.parseInt(s2);
         }
      }

   }

   private void getProductVersionMajorMinor() {
      if (this.databaseProductVersion.indexOf("09") != -1) {
         this.maj = 9;
         if (this.databaseProductVersion.indexOf("01") != -1) {
            this.min = 1;
         }
      } else if (this.databaseProductVersion.indexOf("08") != -1) {
         this.maj = 8;
         this.min = 2;
         if (this.databaseProductVersion.indexOf("01") != -1) {
            this.min = 1;
         }
      }

   }

   protected String getOptimizeClause(Select sel) {
      if (sel != null && sel.getExpectedResultCount() > 0) {
         StringBuffer buf = new StringBuffer();
         buf.append(" ").append(this.optimizeClause).append(" ").append(String.valueOf(sel.getExpectedResultCount())).append(" ").append(this.rowClause);
         return buf.toString();
      } else {
         return "";
      }
   }

   public OpenJPAException newStoreException(String msg, SQLException[] causes, Object failed) {
      if (causes != null && causes.length > 0) {
         msg = this.appendExtendedExceptionMsg(msg, causes[0]);
      }

      return super.newStoreException(msg, causes, failed);
   }

   private String appendExtendedExceptionMsg(String msg, SQLException sqle) {
      String GETSQLCA = "getSqlca";
      String exceptionMsg = new String();

      try {
         Method sqlcaM2 = sqle.getNextException().getClass().getMethod("getSqlca", (Class[])null);
         Object sqlca = sqlcaM2.invoke(sqle.getNextException());
         Method getSqlErrpMethd = sqlca.getClass().getMethod("getSqlErrp", (Class[])null);
         Method getSqlWarnMethd = sqlca.getClass().getMethod("getSqlWarn", (Class[])null);
         Method getSqlErrdMethd = sqlca.getClass().getMethod("getSqlErrd", (Class[])null);
         StringBuffer errdStr = new StringBuffer();
         int[] errds = (int[])((int[])getSqlErrdMethd.invoke(sqlca));

         for(int i = 0; i < errds.length; ++i) {
            errdStr.append(errdStr.length() > 0 ? ", " : "").append(errds[i]);
         }

         exceptionMsg = exceptionMsg.concat("SQLCA OUTPUT[Errp=" + getSqlErrpMethd.invoke(sqlca) + ", Errd=" + errdStr);
         String Warn = new String((char[])((char[])getSqlWarnMethd.invoke(sqlca)));
         if (Warn.trim().length() != 0) {
            exceptionMsg = exceptionMsg.concat(", Warn=" + Warn + "]");
         } else {
            exceptionMsg = exceptionMsg.concat("]");
         }

         msg = msg.concat(exceptionMsg);
         return msg;
      } catch (Throwable var13) {
         return sqle.getMessage();
      }
   }

   public int getDb2ServerType() {
      return this.db2ServerType;
   }

   protected void appendLength(SQLBuffer buf, int type) {
      if (type == 12) {
         buf.append("(").append(Integer.toString(this.characterColumnSize)).append(")");
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
      boolean castrhs = false;
      Class rc = Filters.wrap(rhs.getType());
      int type = 0;
      if (rhs.isConstant()) {
         type = this.getJDBCType(JavaTypes.getTypeCode(rc), false);
         castrhs = true;
      }

      this.appendXmlExists(buf, lhs);
      buf.append(" ").append(op).append(" ");
      buf.append("$");
      if (castrhs) {
         buf.append("Parm");
      } else {
         rhs.appendTo(buf);
      }

      buf.append("]' PASSING ");
      this.appendXmlVar(buf, lhs);
      buf.append(", ");
      if (castrhs) {
         this.appendCast(buf, rhs, type);
      } else {
         rhs.appendTo(buf);
      }

      buf.append(" AS \"");
      if (castrhs) {
         buf.append("Parm");
      } else {
         rhs.appendTo(buf);
      }

      buf.append("\")");
   }

   private void appendXmlComparison2(SQLBuffer buf, String op, FilterValue lhs, FilterValue rhs) {
      this.appendXmlExists(buf, lhs);
      buf.append(" ").append(op).append(" ");
      buf.append("$").append(rhs.getColumnAlias(rhs.getFieldMapping().getColumns()[0])).append("/*/");
      rhs.appendTo(buf);
      buf.append("]' PASSING ");
      this.appendXmlVar(buf, lhs);
      buf.append(", ");
      this.appendXmlVar(buf, rhs);
      buf.append(")");
   }

   private void appendXmlVar(SQLBuffer buf, FilterValue val) {
      buf.append(val.getColumnAlias(val.getFieldMapping().getColumns()[0])).append(" AS ").append("\"").append(val.getColumnAlias(val.getFieldMapping().getColumns()[0])).append("\"");
   }

   private void appendXmlExists(SQLBuffer buf, FilterValue val) {
      buf.append("XMLEXISTS('");
      buf.append("$").append(val.getColumnAlias(val.getFieldMapping().getColumns()[0])).append("/*[");
      val.appendTo(buf);
   }

   private String addCastAsString(String func, String target, String asString) {
      String fstring = func;
      if (func.indexOf(target) != -1) {
         fstring = Strings.replace(func, target, "CAST(" + target + asString + ")");
      }

      return fstring;
   }

   public String addCastAsType(String func, Val val) {
      String fstring = null;
      String type = this.getTypeName(this.getJDBCType(JavaTypes.getTypeCode(val.getType()), false));
      if (String.class.equals(val.getType())) {
         type = type + "(" + this.characterColumnSize + ")";
      }

      fstring = "CAST(? AS " + type + ")";
      return fstring;
   }

   public int getBatchLimit() {
      int limit = super.getBatchLimit();
      if (limit == -1) {
         limit = this.defaultBatchLimit;
         if (this.log.isTraceEnabled()) {
            this.log.trace(_loc.get("batch_unlimit", (Object)String.valueOf(limit)));
         }
      }

      return limit;
   }

   public String getCastFunction(Val val, String func) {
      if ((val instanceof Lit || val instanceof Param) && func.indexOf("VARCHAR") == -1) {
         func = this.addCastAsString(func, "{0}", " AS VARCHAR(1000)");
      }

      return func;
   }

   public void indexOf(SQLBuffer buf, FilterValue str, FilterValue find, FilterValue start) {
      if (find.getValue() != null) {
         buf.append("(LOCATE(CAST((");
         find.appendTo(buf);
         buf.append(") AS VARCHAR(1000)), ");
      } else {
         buf.append("(LOCATE(");
         find.appendTo(buf);
         buf.append(", ");
      }

      if (str.getValue() != null) {
         buf.append("CAST((");
         str.appendTo(buf);
         buf.append(") AS VARCHAR(1000))");
      } else {
         str.appendTo(buf);
      }

      if (start != null) {
         if (start.getValue() == null) {
            buf.append(", CAST((");
            start.appendTo(buf);
            buf.append(") AS INTEGER) + 1");
         } else {
            buf.append(", ");
            start.appendTo(buf);
         }
      }

      buf.append(") - 1)");
   }

   public void appendCast(SQLBuffer buf, FilterValue val, int type) {
      int firstParam = this.castFunction.indexOf("{0}");
      String pre = this.castFunction.substring(0, firstParam);
      String mid = this.castFunction.substring(firstParam + 3);
      int secondParam = mid.indexOf("{1}");
      String post;
      if (secondParam > -1) {
         post = mid.substring(secondParam + 3);
         mid = mid.substring(0, secondParam);
      } else {
         post = "";
      }

      if (!(val instanceof Lit) && !(val instanceof Param)) {
         val.appendTo(buf);
         String sqlString = buf.getSQL(false);
         if (sqlString.endsWith("?")) {
            String typeName = this.getTypeName(type);
            if (String.class.equals(val.getType())) {
               typeName = typeName + "(" + this.characterColumnSize + ")";
            }

            String str = "CAST(? AS " + typeName + ")";
            buf.replaceSqlString(sqlString.length() - 1, sqlString.length(), str);
         }
      } else {
         buf.append(pre);
         val.appendTo(buf);
         buf.append(mid);
         buf.append(this.getTypeName(type));
         this.appendLength(buf, type);
         buf.append(post);
      }

   }

   public void createIndexIfNecessary(Schema schema, String table, Column pkColumn) {
      if (this.isDB2ZOSV8xOrLater()) {
         Table tab = schema.getTable(table);
         Index idx = tab.addIndex(tab.getFullName() + "_IDX");
         idx.setUnique(true);
         idx.addColumn(pkColumn);
      }

   }
}
