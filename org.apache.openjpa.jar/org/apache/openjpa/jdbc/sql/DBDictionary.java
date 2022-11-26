package org.apache.openjpa.jdbc.sql;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.CharArrayReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.io.StringReader;
import java.io.Writer;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Array;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Ref;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import javax.sql.DataSource;
import org.apache.commons.lang.StringUtils;
import org.apache.openjpa.jdbc.conf.JDBCConfiguration;
import org.apache.openjpa.jdbc.kernel.JDBCFetchConfiguration;
import org.apache.openjpa.jdbc.kernel.JDBCStore;
import org.apache.openjpa.jdbc.kernel.exps.ExpContext;
import org.apache.openjpa.jdbc.kernel.exps.ExpState;
import org.apache.openjpa.jdbc.kernel.exps.FilterValue;
import org.apache.openjpa.jdbc.kernel.exps.Val;
import org.apache.openjpa.jdbc.meta.ClassMapping;
import org.apache.openjpa.jdbc.meta.FieldMapping;
import org.apache.openjpa.jdbc.schema.Column;
import org.apache.openjpa.jdbc.schema.DataSourceFactory;
import org.apache.openjpa.jdbc.schema.ForeignKey;
import org.apache.openjpa.jdbc.schema.Index;
import org.apache.openjpa.jdbc.schema.NameSet;
import org.apache.openjpa.jdbc.schema.PrimaryKey;
import org.apache.openjpa.jdbc.schema.Schema;
import org.apache.openjpa.jdbc.schema.SchemaGroup;
import org.apache.openjpa.jdbc.schema.Sequence;
import org.apache.openjpa.jdbc.schema.Table;
import org.apache.openjpa.jdbc.schema.Unique;
import org.apache.openjpa.kernel.Filters;
import org.apache.openjpa.kernel.OpenJPAStateManager;
import org.apache.openjpa.kernel.StoreContext;
import org.apache.openjpa.kernel.exps.Path;
import org.apache.openjpa.lib.conf.Configurable;
import org.apache.openjpa.lib.conf.Configuration;
import org.apache.openjpa.lib.jdbc.ConnectionDecorator;
import org.apache.openjpa.lib.jdbc.LoggingConnectionDecorator;
import org.apache.openjpa.lib.log.Log;
import org.apache.openjpa.lib.util.Localizer;
import org.apache.openjpa.meta.FieldMetaData;
import org.apache.openjpa.meta.JavaTypes;
import org.apache.openjpa.util.GeneralException;
import org.apache.openjpa.util.InternalException;
import org.apache.openjpa.util.InvalidStateException;
import org.apache.openjpa.util.LockException;
import org.apache.openjpa.util.ObjectExistsException;
import org.apache.openjpa.util.ObjectNotFoundException;
import org.apache.openjpa.util.OpenJPAException;
import org.apache.openjpa.util.OptimisticException;
import org.apache.openjpa.util.ReferentialIntegrityException;
import org.apache.openjpa.util.Serialization;
import org.apache.openjpa.util.StoreException;
import org.apache.openjpa.util.UnsupportedException;
import org.apache.openjpa.util.UserException;
import serp.util.Numbers;
import serp.util.Strings;

public class DBDictionary implements Configurable, ConnectionDecorator, JoinSyntaxes, LoggingConnectionDecorator.SQLWarningHandler {
   public static final String VENDOR_OTHER = "other";
   public static final String VENDOR_DATADIRECT = "datadirect";
   public static final String SCHEMA_CASE_UPPER = "upper";
   public static final String SCHEMA_CASE_LOWER = "lower";
   public static final String SCHEMA_CASE_PRESERVE = "preserve";
   public static final String CONS_NAME_BEFORE = "before";
   public static final String CONS_NAME_MID = "mid";
   public static final String CONS_NAME_AFTER = "after";
   public int blobBufferSize = 50000;
   public int clobBufferSize = 50000;
   protected static final int RANGE_POST_SELECT = 0;
   protected static final int RANGE_PRE_DISTINCT = 1;
   protected static final int RANGE_POST_DISTINCT = 2;
   protected static final int RANGE_POST_LOCK = 3;
   protected static final int NANO = 1;
   protected static final int MICRO = 1000;
   protected static final int MILLI = 1000000;
   protected static final int CENTI = 10000000;
   protected static final int DECI = 100000000;
   protected static final int SEC = 1000000000;
   protected static final int NAME_ANY = 0;
   protected static final int NAME_TABLE = 1;
   protected static final int NAME_SEQUENCE = 2;
   protected static final int UNLIMITED = -1;
   protected static final int NO_BATCH = 0;
   private static final String ZERO_DATE_STR = "'" + new Date(0L) + "'";
   private static final String ZERO_TIME_STR = "'" + new Time(0L) + "'";
   private static final String ZERO_TIMESTAMP_STR = "'" + new Timestamp(0L) + "'";
   private static final Localizer _loc = Localizer.forPackage(DBDictionary.class);
   public String platform = "Generic";
   public String driverVendor = null;
   public String catalogSeparator = ".";
   public boolean createPrimaryKeys = true;
   public String constraintNameMode = "before";
   public int maxTableNameLength = 128;
   public int maxColumnNameLength = 128;
   public int maxConstraintNameLength = 128;
   public int maxIndexNameLength = 128;
   public int maxIndexesPerTable = Integer.MAX_VALUE;
   public boolean supportsForeignKeys = true;
   public boolean supportsTimestampNanos = true;
   public boolean supportsUniqueConstraints = true;
   public boolean supportsDeferredConstraints = true;
   public boolean supportsRestrictDeleteAction = true;
   public boolean supportsCascadeDeleteAction = true;
   public boolean supportsNullDeleteAction = true;
   public boolean supportsDefaultDeleteAction = true;
   public boolean supportsRestrictUpdateAction = true;
   public boolean supportsCascadeUpdateAction = true;
   public boolean supportsNullUpdateAction = true;
   public boolean supportsDefaultUpdateAction = true;
   public boolean supportsAlterTableWithAddColumn = true;
   public boolean supportsAlterTableWithDropColumn = true;
   public boolean supportsComments = false;
   public String reservedWords = null;
   public String systemSchemas = null;
   public String systemTables = null;
   public String selectWords = null;
   public String fixedSizeTypeNames = null;
   public String schemaCase = "upper";
   public String validationSQL = null;
   public String closePoolSQL = null;
   public String initializationSQL = null;
   public int joinSyntax = 0;
   public String outerJoinClause = "LEFT OUTER JOIN";
   public String innerJoinClause = "INNER JOIN";
   public String crossJoinClause = "CROSS JOIN";
   public boolean requiresConditionForCrossJoin = false;
   public String forUpdateClause = "FOR UPDATE";
   public String tableForUpdateClause = null;
   public String distinctCountColumnSeparator = null;
   public boolean supportsSelectForUpdate = true;
   public boolean supportsLockingWithDistinctClause = true;
   public boolean supportsLockingWithMultipleTables = true;
   public boolean supportsLockingWithOrderClause = true;
   public boolean supportsLockingWithOuterJoin = true;
   public boolean supportsLockingWithInnerJoin = true;
   public boolean supportsLockingWithSelectRange = true;
   public boolean supportsQueryTimeout = true;
   public boolean simulateLocking = false;
   public boolean supportsSubselect = true;
   public boolean supportsCorrelatedSubselect = true;
   public boolean supportsHaving = true;
   public boolean supportsSelectStartIndex = false;
   public boolean supportsSelectEndIndex = false;
   public int rangePosition = 0;
   public boolean requiresAliasForSubselect = false;
   public boolean requiresTargetForDelete = false;
   public boolean allowsAliasInBulkClause = true;
   public boolean supportsMultipleNontransactionalResultSets = true;
   public String searchStringEscape = "\\";
   public boolean requiresCastForMathFunctions = false;
   public boolean requiresCastForComparisons = false;
   public boolean supportsModOperator = false;
   public boolean supportsXMLColumn = false;
   public String castFunction = "CAST({0} AS {1})";
   public String toLowerCaseFunction = "LOWER({0})";
   public String toUpperCaseFunction = "UPPER({0})";
   public String stringLengthFunction = "CHAR_LENGTH({0})";
   public String bitLengthFunction = "(OCTET_LENGTH({0}) * 8)";
   public String trimLeadingFunction = "TRIM(LEADING {1} FROM {0})";
   public String trimTrailingFunction = "TRIM(TRAILING {1} FROM {0})";
   public String trimBothFunction = "TRIM(BOTH {1} FROM {0})";
   public String concatenateFunction = "({0}||{1})";
   public String concatenateDelimiter = "'OPENJPATOKEN'";
   public String substringFunctionName = "SUBSTRING";
   public String currentDateFunction = "CURRENT_DATE";
   public String currentTimeFunction = "CURRENT_TIME";
   public String currentTimestampFunction = "CURRENT_TIMESTAMP";
   public String dropTableSQL = "DROP TABLE {0}";
   public boolean storageLimitationsFatal = false;
   public boolean storeLargeNumbersAsStrings = false;
   public boolean storeCharsAsNumbers = true;
   public boolean useGetBytesForBlobs = false;
   public boolean useSetBytesForBlobs = false;
   public boolean useGetObjectForBlobs = false;
   public boolean useGetStringForClobs = false;
   public boolean useSetStringForClobs = false;
   public int maxEmbeddedBlobSize = -1;
   public int maxEmbeddedClobSize = -1;
   public int inClauseLimit = -1;
   public int datePrecision = 1000000;
   public int characterColumnSize = 255;
   public String arrayTypeName = "ARRAY";
   public String bigintTypeName = "BIGINT";
   public String binaryTypeName = "BINARY";
   public String bitTypeName = "BIT";
   public String blobTypeName = "BLOB";
   public String booleanTypeName = "BOOLEAN";
   public String charTypeName = "CHAR";
   public String clobTypeName = "CLOB";
   public String dateTypeName = "DATE";
   public String decimalTypeName = "DECIMAL";
   public String distinctTypeName = "DISTINCT";
   public String doubleTypeName = "DOUBLE";
   public String floatTypeName = "FLOAT";
   public String integerTypeName = "INTEGER";
   public String javaObjectTypeName = "JAVA_OBJECT";
   public String longVarbinaryTypeName = "LONGVARBINARY";
   public String longVarcharTypeName = "LONGVARCHAR";
   public String nullTypeName = "NULL";
   public String numericTypeName = "NUMERIC";
   public String otherTypeName = "OTHER";
   public String realTypeName = "REAL";
   public String refTypeName = "REF";
   public String smallintTypeName = "SMALLINT";
   public String structTypeName = "STRUCT";
   public String timeTypeName = "TIME";
   public String timestampTypeName = "TIMESTAMP";
   public String tinyintTypeName = "TINYINT";
   public String varbinaryTypeName = "VARBINARY";
   public String varcharTypeName = "VARCHAR";
   public String xmlTypeName = "XML";
   public String getStringVal = "";
   public boolean useSchemaName = true;
   public String tableTypes = "TABLE";
   public boolean supportsSchemaForGetTables = true;
   public boolean supportsSchemaForGetColumns = true;
   public boolean supportsNullTableForGetColumns = true;
   public boolean supportsNullTableForGetPrimaryKeys = false;
   public boolean supportsNullTableForGetIndexInfo = false;
   public boolean supportsNullTableForGetImportedKeys = false;
   public boolean useGetBestRowIdentifierForPrimaryKeys = false;
   public boolean requiresAutoCommitForMetaData = false;
   public int maxAutoAssignNameLength = 31;
   public String autoAssignClause = null;
   public String autoAssignTypeName = null;
   public boolean supportsAutoAssign = false;
   public String lastGeneratedKeyQuery = null;
   public String nextSequenceQuery = null;
   public String sequenceSQL = null;
   public String sequenceSchemaSQL = null;
   public String sequenceNameSQL = null;
   protected JDBCConfiguration conf = null;
   protected Log log = null;
   protected boolean connected = false;
   protected final Set reservedWordSet = new HashSet();
   protected final Set systemSchemaSet = new HashSet();
   protected final Set systemTableSet = new HashSet();
   protected final Set fixedSizeTypeNameSet = new HashSet();
   protected final Set typeModifierSet = new HashSet();
   protected final Set selectWordSet = new HashSet();
   private Set _precisionWarnedTypes = null;
   private Method _setBytes = null;
   private Method _setString = null;
   private Method _setCharStream = null;
   public int batchLimit = 0;
   public final Map sqlStateCodes = new HashMap();

   public DBDictionary() {
      this.fixedSizeTypeNameSet.addAll(Arrays.asList("BIGINT", "BIT", "BLOB", "CLOB", "DATE", "DECIMAL", "DISTINCT", "DOUBLE", "FLOAT", "INTEGER", "JAVA_OBJECT", "NULL", "NUMERIC", "OTHER", "REAL", "REF", "SMALLINT", "STRUCT", "TIME", "TIMESTAMP", "TINYINT"));
      this.selectWordSet.add("SELECT");
   }

   public void connectedConfiguration(Connection conn) throws SQLException {
      if (!this.connected) {
         try {
            if (this.log.isTraceEnabled()) {
               this.log.trace(DBDictionaryFactory.toString(conn.getMetaData()));
            }
         } catch (Exception var3) {
            this.log.trace(var3.toString(), var3);
         }
      }

      this.connected = true;
   }

   public Array getArray(ResultSet rs, int column) throws SQLException {
      return rs.getArray(column);
   }

   public InputStream getAsciiStream(ResultSet rs, int column) throws SQLException {
      return rs.getAsciiStream(column);
   }

   public BigDecimal getBigDecimal(ResultSet rs, int column) throws SQLException {
      if (this.storeLargeNumbersAsStrings) {
         String str = this.getString(rs, column);
         return str == null ? null : new BigDecimal(str);
      } else {
         return rs.getBigDecimal(column);
      }
   }

   public Number getNumber(ResultSet rs, int column) throws SQLException {
      try {
         return this.getBigDecimal(rs, column);
      } catch (Exception var12) {
         try {
            return new Double(this.getDouble(rs, column));
         } catch (Exception var11) {
            try {
               return new Float(this.getFloat(rs, column));
            } catch (Exception var10) {
               try {
                  return Numbers.valueOf(this.getLong(rs, column));
               } catch (Exception var9) {
                  try {
                     return Numbers.valueOf(this.getInt(rs, column));
                  } catch (Exception var8) {
                     if (var12 instanceof RuntimeException) {
                        throw (RuntimeException)var12;
                     } else if (var12 instanceof SQLException) {
                        throw (SQLException)var12;
                     } else {
                        return null;
                     }
                  }
               }
            }
         }
      }
   }

   public BigInteger getBigInteger(ResultSet rs, int column) throws SQLException {
      if (this.storeLargeNumbersAsStrings) {
         String str = this.getString(rs, column);
         return str == null ? null : (new BigDecimal(str)).toBigInteger();
      } else {
         BigDecimal bd = this.getBigDecimal(rs, column);
         return bd == null ? null : bd.toBigInteger();
      }
   }

   public InputStream getBinaryStream(ResultSet rs, int column) throws SQLException {
      return rs.getBinaryStream(column);
   }

   public InputStream getLOBStream(JDBCStore store, ResultSet rs, int column) throws SQLException {
      return rs.getBinaryStream(column);
   }

   public Blob getBlob(ResultSet rs, int column) throws SQLException {
      return rs.getBlob(column);
   }

   public Object getBlobObject(ResultSet rs, int column, JDBCStore store) throws SQLException {
      InputStream in = null;
      if (!this.useGetBytesForBlobs && !this.useGetObjectForBlobs) {
         Blob blob = this.getBlob(rs, column);
         if (blob != null && blob.length() > 0L) {
            in = blob.getBinaryStream();
         }
      } else {
         byte[] bytes = this.getBytes(rs, column);
         if (bytes != null && bytes.length > 0) {
            in = new ByteArrayInputStream(bytes);
         }
      }

      if (in == null) {
         return null;
      } else {
         Object var16;
         try {
            if (store == null) {
               var16 = Serialization.deserialize((InputStream)in, (StoreContext)null);
               return var16;
            }

            var16 = Serialization.deserialize((InputStream)in, store.getContext());
         } finally {
            try {
               ((InputStream)in).close();
            } catch (IOException var13) {
            }

         }

         return var16;
      }
   }

   public boolean getBoolean(ResultSet rs, int column) throws SQLException {
      return rs.getBoolean(column);
   }

   public byte getByte(ResultSet rs, int column) throws SQLException {
      return rs.getByte(column);
   }

   public byte[] getBytes(ResultSet rs, int column) throws SQLException {
      if (this.useGetBytesForBlobs) {
         return rs.getBytes(column);
      } else if (this.useGetObjectForBlobs) {
         return (byte[])((byte[])rs.getObject(column));
      } else {
         Blob blob = this.getBlob(rs, column);
         if (blob == null) {
            return null;
         } else {
            int length = (int)blob.length();
            return length == 0 ? null : blob.getBytes(1L, length);
         }
      }
   }

   public Calendar getCalendar(ResultSet rs, int column) throws SQLException {
      java.util.Date d = this.getDate(rs, column);
      if (d == null) {
         return null;
      } else {
         Calendar cal = Calendar.getInstance();
         cal.setTime(d);
         return cal;
      }
   }

   public char getChar(ResultSet rs, int column) throws SQLException {
      if (this.storeCharsAsNumbers) {
         return (char)this.getInt(rs, column);
      } else {
         String str = this.getString(rs, column);
         return StringUtils.isEmpty(str) ? '\u0000' : str.charAt(0);
      }
   }

   public Reader getCharacterStream(ResultSet rs, int column) throws SQLException {
      return rs.getCharacterStream(column);
   }

   public Clob getClob(ResultSet rs, int column) throws SQLException {
      return rs.getClob(column);
   }

   public String getClobString(ResultSet rs, int column) throws SQLException {
      if (this.useGetStringForClobs) {
         return rs.getString(column);
      } else {
         Clob clob = this.getClob(rs, column);
         if (clob == null) {
            return null;
         } else {
            return clob.length() == 0L ? "" : clob.getSubString(1L, (int)clob.length());
         }
      }
   }

   public java.util.Date getDate(ResultSet rs, int column) throws SQLException {
      Timestamp tstamp = this.getTimestamp(rs, column, (Calendar)null);
      if (tstamp == null) {
         return null;
      } else {
         int fractional = (int)Math.round((double)tstamp.getNanos() / 1000000.0);
         long millis = tstamp.getTime() / 1000L * 1000L;
         return new java.util.Date(millis + (long)fractional);
      }
   }

   public Date getDate(ResultSet rs, int column, Calendar cal) throws SQLException {
      return cal == null ? rs.getDate(column) : rs.getDate(column, cal);
   }

   public double getDouble(ResultSet rs, int column) throws SQLException {
      return rs.getDouble(column);
   }

   public float getFloat(ResultSet rs, int column) throws SQLException {
      return rs.getFloat(column);
   }

   public int getInt(ResultSet rs, int column) throws SQLException {
      return rs.getInt(column);
   }

   public Locale getLocale(ResultSet rs, int column) throws SQLException {
      String str = this.getString(rs, column);
      if (StringUtils.isEmpty(str)) {
         return null;
      } else {
         String[] params = Strings.split(str, "_", 3);
         return params.length < 3 ? null : new Locale(params[0], params[1], params[2]);
      }
   }

   public long getLong(ResultSet rs, int column) throws SQLException {
      return rs.getLong(column);
   }

   public Object getObject(ResultSet rs, int column, Map map) throws SQLException {
      return map == null ? rs.getObject(column) : rs.getObject(column, map);
   }

   public Ref getRef(ResultSet rs, int column, Map map) throws SQLException {
      return rs.getRef(column);
   }

   public short getShort(ResultSet rs, int column) throws SQLException {
      return rs.getShort(column);
   }

   public String getString(ResultSet rs, int column) throws SQLException {
      return rs.getString(column);
   }

   public Time getTime(ResultSet rs, int column, Calendar cal) throws SQLException {
      return cal == null ? rs.getTime(column) : rs.getTime(column, cal);
   }

   public Timestamp getTimestamp(ResultSet rs, int column, Calendar cal) throws SQLException {
      return cal == null ? rs.getTimestamp(column) : rs.getTimestamp(column, cal);
   }

   public void setArray(PreparedStatement stmnt, int idx, Array val, Column col) throws SQLException {
      stmnt.setArray(idx, val);
   }

   public void setAsciiStream(PreparedStatement stmnt, int idx, InputStream val, int length, Column col) throws SQLException {
      stmnt.setAsciiStream(idx, val, length);
   }

   public void setBigDecimal(PreparedStatement stmnt, int idx, BigDecimal val, Column col) throws SQLException {
      if ((col == null || !col.isCompatible(12, (String)null, 0, 0)) && (col != null || !this.storeLargeNumbersAsStrings)) {
         stmnt.setBigDecimal(idx, val);
      } else {
         this.setString(stmnt, idx, val.toString(), col);
      }

   }

   public void setBigInteger(PreparedStatement stmnt, int idx, BigInteger val, Column col) throws SQLException {
      if ((col == null || !col.isCompatible(12, (String)null, 0, 0)) && (col != null || !this.storeLargeNumbersAsStrings)) {
         this.setBigDecimal(stmnt, idx, new BigDecimal(val), col);
      } else {
         this.setString(stmnt, idx, val.toString(), col);
      }

   }

   public void setBinaryStream(PreparedStatement stmnt, int idx, InputStream val, int length, Column col) throws SQLException {
      stmnt.setBinaryStream(idx, val, length);
   }

   public void setBlob(PreparedStatement stmnt, int idx, Blob val, Column col) throws SQLException {
      stmnt.setBlob(idx, val);
   }

   public void setBlobObject(PreparedStatement stmnt, int idx, Object val, Column col, JDBCStore store) throws SQLException {
      this.setBytes(stmnt, idx, this.serialize(val, store), col);
   }

   public void setBoolean(PreparedStatement stmnt, int idx, boolean val, Column col) throws SQLException {
      stmnt.setInt(idx, val ? 1 : 0);
   }

   public void setByte(PreparedStatement stmnt, int idx, byte val, Column col) throws SQLException {
      stmnt.setByte(idx, val);
   }

   public void setBytes(PreparedStatement stmnt, int idx, byte[] val, Column col) throws SQLException {
      if (this.useSetBytesForBlobs) {
         stmnt.setBytes(idx, val);
      } else {
         this.setBinaryStream(stmnt, idx, new ByteArrayInputStream(val), val.length, col);
      }

   }

   public void setChar(PreparedStatement stmnt, int idx, char val, Column col) throws SQLException {
      if ((col == null || !col.isCompatible(4, (String)null, 0, 0)) && (col != null || !this.storeCharsAsNumbers)) {
         this.setString(stmnt, idx, String.valueOf(val), col);
      } else {
         this.setInt(stmnt, idx, val, col);
      }

   }

   public void setCharacterStream(PreparedStatement stmnt, int idx, Reader val, int length, Column col) throws SQLException {
      stmnt.setCharacterStream(idx, val, length);
   }

   public void setClob(PreparedStatement stmnt, int idx, Clob val, Column col) throws SQLException {
      stmnt.setClob(idx, val);
   }

   public void setClobString(PreparedStatement stmnt, int idx, String val, Column col) throws SQLException {
      if (this.useSetStringForClobs) {
         stmnt.setString(idx, val);
      } else {
         StringReader in = new StringReader(val);
         this.setCharacterStream(stmnt, idx, in, val.length(), col);
      }

   }

   public void setDate(PreparedStatement stmnt, int idx, java.util.Date val, Column col) throws SQLException {
      if (col != null && col.getType() == 91) {
         this.setDate(stmnt, idx, new Date(val.getTime()), (Calendar)null, col);
      } else if (col != null && col.getType() == 92) {
         this.setTime(stmnt, idx, new Time(val.getTime()), (Calendar)null, col);
      } else if (val instanceof Timestamp) {
         this.setTimestamp(stmnt, idx, (Timestamp)val, (Calendar)null, col);
      } else {
         this.setTimestamp(stmnt, idx, new Timestamp(val.getTime()), (Calendar)null, col);
      }

   }

   public void setDate(PreparedStatement stmnt, int idx, Date val, Calendar cal, Column col) throws SQLException {
      if (cal == null) {
         stmnt.setDate(idx, val);
      } else {
         stmnt.setDate(idx, val, cal);
      }

   }

   public void setCalendar(PreparedStatement stmnt, int idx, Calendar val, Column col) throws SQLException {
      this.setDate(stmnt, idx, val.getTime(), col);
   }

   public void setDouble(PreparedStatement stmnt, int idx, double val, Column col) throws SQLException {
      stmnt.setDouble(idx, val);
   }

   public void setFloat(PreparedStatement stmnt, int idx, float val, Column col) throws SQLException {
      stmnt.setFloat(idx, val);
   }

   public void setInt(PreparedStatement stmnt, int idx, int val, Column col) throws SQLException {
      stmnt.setInt(idx, val);
   }

   public void setLong(PreparedStatement stmnt, int idx, long val, Column col) throws SQLException {
      stmnt.setLong(idx, val);
   }

   public void setLocale(PreparedStatement stmnt, int idx, Locale val, Column col) throws SQLException {
      this.setString(stmnt, idx, val.getLanguage() + "_" + val.getCountry() + "_" + val.getVariant(), col);
   }

   public void setNull(PreparedStatement stmnt, int idx, int colType, Column col) throws SQLException {
      stmnt.setNull(idx, colType);
   }

   public void setNumber(PreparedStatement stmnt, int idx, Number num, Column col) throws SQLException {
      if (num instanceof Double) {
         this.setDouble(stmnt, idx, (Double)num, col);
      } else if (num instanceof Float) {
         this.setFloat(stmnt, idx, (Float)num, col);
      } else {
         this.setBigDecimal(stmnt, idx, new BigDecimal(num.toString()), col);
      }

   }

   public void setObject(PreparedStatement stmnt, int idx, Object val, int colType, Column col) throws SQLException {
      if (colType != -1 && colType != 1111) {
         stmnt.setObject(idx, val, colType);
      } else {
         stmnt.setObject(idx, val);
      }

   }

   public void setRef(PreparedStatement stmnt, int idx, Ref val, Column col) throws SQLException {
      stmnt.setRef(idx, val);
   }

   public void setShort(PreparedStatement stmnt, int idx, short val, Column col) throws SQLException {
      stmnt.setShort(idx, val);
   }

   public void setString(PreparedStatement stmnt, int idx, String val, Column col) throws SQLException {
      stmnt.setString(idx, val);
   }

   public void setTime(PreparedStatement stmnt, int idx, Time val, Calendar cal, Column col) throws SQLException {
      if (cal == null) {
         stmnt.setTime(idx, val);
      } else {
         stmnt.setTime(idx, val, cal);
      }

   }

   public void setTimestamp(PreparedStatement stmnt, int idx, Timestamp val, Calendar cal, Column col) throws SQLException {
      int rounded = (int)Math.round((double)val.getNanos() / (double)this.datePrecision);
      int nanos = rounded * this.datePrecision;
      if (nanos > 999999999) {
         val.setTime(val.getTime() + 1000L);
         nanos = 0;
      }

      if (this.supportsTimestampNanos) {
         val.setNanos(nanos);
      } else {
         val.setNanos(0);
      }

      if (cal == null) {
         stmnt.setTimestamp(idx, val);
      } else {
         stmnt.setTimestamp(idx, val, cal);
      }

   }

   public void setTyped(PreparedStatement stmnt, int idx, Object val, Column col, int type, JDBCStore store) throws SQLException {
      if (val == null) {
         this.setNull(stmnt, idx, col == null ? 1111 : col.getType(), col);
      } else {
         Sized s;
         Calendard c;
         switch (type) {
            case 0:
            case 16:
               this.setBoolean(stmnt, idx, (Boolean)val, col);
               break;
            case 1:
            case 17:
               this.setByte(stmnt, idx, ((Number)val).byteValue(), col);
               break;
            case 2:
            case 18:
               this.setChar(stmnt, idx, (Character)val, col);
               break;
            case 3:
            case 19:
               this.setDouble(stmnt, idx, ((Number)val).doubleValue(), col);
               break;
            case 4:
            case 20:
               this.setFloat(stmnt, idx, ((Number)val).floatValue(), col);
               break;
            case 5:
            case 21:
               this.setInt(stmnt, idx, ((Number)val).intValue(), col);
               break;
            case 6:
            case 22:
               this.setLong(stmnt, idx, ((Number)val).longValue(), col);
               break;
            case 7:
            case 23:
               this.setShort(stmnt, idx, ((Number)val).shortValue(), col);
               break;
            case 8:
               this.setBlobObject(stmnt, idx, val, col, store);
               break;
            case 9:
               if (col == null || col.getType() != 2005 && col.getType() != -1) {
                  this.setString(stmnt, idx, (String)val, col);
               } else {
                  this.setClobString(stmnt, idx, (String)val, col);
               }
               break;
            case 10:
               this.setNumber(stmnt, idx, (Number)val, col);
               break;
            case 14:
               this.setDate(stmnt, idx, (java.util.Date)val, col);
               break;
            case 24:
               this.setBigDecimal(stmnt, idx, (BigDecimal)val, col);
               break;
            case 25:
               this.setBigInteger(stmnt, idx, (BigInteger)val, col);
               break;
            case 26:
               this.setLocale(stmnt, idx, (Locale)val, col);
               break;
            case 28:
               this.setCalendar(stmnt, idx, (Calendar)val, col);
               break;
            case 1000:
               this.setArray(stmnt, idx, (Array)val, col);
               break;
            case 1001:
               s = (Sized)val;
               this.setAsciiStream(stmnt, idx, (InputStream)s.value, s.size, col);
               break;
            case 1002:
               s = (Sized)val;
               this.setBinaryStream(stmnt, idx, (InputStream)s.value, s.size, col);
               break;
            case 1003:
               this.setBlob(stmnt, idx, (Blob)val, col);
               break;
            case 1004:
               this.setBytes(stmnt, idx, (byte[])((byte[])val), col);
               break;
            case 1005:
               s = (Sized)val;
               this.setCharacterStream(stmnt, idx, (Reader)s.value, s.size, col);
               break;
            case 1006:
               this.setClob(stmnt, idx, (Clob)val, col);
               break;
            case 1007:
               if (val instanceof Calendard) {
                  c = (Calendard)val;
                  this.setDate(stmnt, idx, (Date)c.value, c.calendar, col);
               } else {
                  this.setDate(stmnt, idx, (Date)val, (Calendar)null, col);
               }
               break;
            case 1009:
               this.setRef(stmnt, idx, (Ref)val, col);
               break;
            case 1010:
               if (val instanceof Calendard) {
                  c = (Calendard)val;
                  this.setTime(stmnt, idx, (Time)c.value, c.calendar, col);
               } else {
                  this.setTime(stmnt, idx, (Time)val, (Calendar)null, col);
               }
               break;
            case 1011:
               if (val instanceof Calendard) {
                  c = (Calendard)val;
                  this.setTimestamp(stmnt, idx, (Timestamp)c.value, c.calendar, col);
               } else {
                  this.setTimestamp(stmnt, idx, (Timestamp)val, (Calendar)null, col);
               }
               break;
            default:
               if (col != null && (col.getType() == 2004 || col.getType() == -3)) {
                  this.setBlobObject(stmnt, idx, val, col, store);
               } else {
                  this.setObject(stmnt, idx, val, col.getType(), col);
               }
         }

      }
   }

   public void setUnknown(PreparedStatement stmnt, int idx, Object val, Column col) throws SQLException {
      Sized sized = null;
      Calendard cald = null;
      if (val instanceof Sized) {
         sized = (Sized)val;
         val = sized.value;
      } else if (val instanceof Calendard) {
         cald = (Calendard)val;
         val = cald.value;
      }

      if (val == null) {
         this.setNull(stmnt, idx, col == null ? 1111 : col.getType(), col);
      } else if (val instanceof String) {
         this.setString(stmnt, idx, val.toString(), col);
      } else if (val instanceof Integer) {
         this.setInt(stmnt, idx, (Integer)val, col);
      } else if (val instanceof Boolean) {
         this.setBoolean(stmnt, idx, (Boolean)val, col);
      } else if (val instanceof Long) {
         this.setLong(stmnt, idx, (Long)val, col);
      } else if (val instanceof Float) {
         this.setFloat(stmnt, idx, (Float)val, col);
      } else if (val instanceof Double) {
         this.setDouble(stmnt, idx, (Double)val, col);
      } else if (val instanceof Byte) {
         this.setByte(stmnt, idx, (Byte)val, col);
      } else if (val instanceof Character) {
         this.setChar(stmnt, idx, (Character)val, col);
      } else if (val instanceof Short) {
         this.setShort(stmnt, idx, (Short)val, col);
      } else if (val instanceof Locale) {
         this.setLocale(stmnt, idx, (Locale)val, col);
      } else if (val instanceof BigDecimal) {
         this.setBigDecimal(stmnt, idx, (BigDecimal)val, col);
      } else if (val instanceof BigInteger) {
         this.setBigInteger(stmnt, idx, (BigInteger)val, col);
      } else if (val instanceof Array) {
         this.setArray(stmnt, idx, (Array)val, col);
      } else if (val instanceof Blob) {
         this.setBlob(stmnt, idx, (Blob)val, col);
      } else if (val instanceof byte[]) {
         this.setBytes(stmnt, idx, (byte[])((byte[])val), col);
      } else if (val instanceof Clob) {
         this.setClob(stmnt, idx, (Clob)val, col);
      } else if (val instanceof Ref) {
         this.setRef(stmnt, idx, (Ref)val, col);
      } else if (val instanceof Date) {
         this.setDate(stmnt, idx, (Date)val, cald == null ? null : cald.calendar, col);
      } else if (val instanceof Timestamp) {
         this.setTimestamp(stmnt, idx, (Timestamp)val, cald == null ? null : cald.calendar, col);
      } else if (val instanceof Time) {
         this.setTime(stmnt, idx, (Time)val, cald == null ? null : cald.calendar, col);
      } else if (val instanceof java.util.Date) {
         this.setDate(stmnt, idx, (java.util.Date)val, col);
      } else if (val instanceof Calendar) {
         this.setDate(stmnt, idx, ((Calendar)val).getTime(), col);
      } else {
         if (!(val instanceof Reader)) {
            throw new UserException(_loc.get("bad-param", (Object)val.getClass()));
         }

         this.setCharacterStream(stmnt, idx, (Reader)val, sized == null ? 0 : sized.size, col);
      }

   }

   public byte[] serialize(Object val, JDBCStore store) throws SQLException {
      if (val == null) {
         return null;
      } else {
         return val instanceof SerializedData ? ((SerializedData)val).bytes : Serialization.serialize(val, store.getContext());
      }
   }

   public void putBytes(Object blob, byte[] data) throws SQLException {
      if (this._setBytes == null) {
         try {
            this._setBytes = blob.getClass().getMethod("setBytes", Long.TYPE, byte[].class);
         } catch (Exception var4) {
            throw new StoreException(var4);
         }
      }

      invokePutLobMethod(this._setBytes, blob, new Object[]{Numbers.valueOf(1L), data});
   }

   public void putString(Object clob, String data) throws SQLException {
      if (this._setString == null) {
         try {
            this._setString = clob.getClass().getMethod("setString", Long.TYPE, String.class);
         } catch (Exception var4) {
            throw new StoreException(var4);
         }
      }

      invokePutLobMethod(this._setString, clob, new Object[]{Numbers.valueOf(1L), data});
   }

   public void putChars(Object clob, char[] data) throws SQLException {
      if (this._setCharStream == null) {
         try {
            this._setCharStream = clob.getClass().getMethod("setCharacterStream", Long.TYPE);
         } catch (Exception var6) {
            throw new StoreException(var6);
         }
      }

      Writer writer = (Writer)invokePutLobMethod(this._setCharStream, clob, new Object[]{Numbers.valueOf(1L)});

      try {
         writer.write(data);
         writer.flush();
      } catch (IOException var5) {
         throw new SQLException(var5.toString());
      }
   }

   private static Object invokePutLobMethod(Method method, Object target, Object[] args) throws SQLException {
      try {
         return method.invoke(target, args);
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

   protected void storageWarning(Object orig, Object converted) {
      boolean warn;
      synchronized(this) {
         if (this._precisionWarnedTypes == null) {
            this._precisionWarnedTypes = new HashSet();
         }

         warn = this._precisionWarnedTypes.add(orig.getClass());
      }

      if (this.storageLimitationsFatal || warn && this.log.isWarnEnabled() || !warn && this.log.isTraceEnabled()) {
         Localizer.Message msg = _loc.get("storage-restriction", new Object[]{this.platform, orig, orig.getClass().getName(), converted});
         if (this.storageLimitationsFatal) {
            throw new StoreException(msg);
         }

         if (warn) {
            this.log.warn(msg);
         } else {
            this.log.trace(msg);
         }
      }

   }

   public int getJDBCType(int metaTypeCode, boolean lob) {
      if (lob) {
         switch (metaTypeCode) {
            case 9:
            case 1001:
            case 1005:
               return this.getPreferredType(2005);
            default:
               return this.getPreferredType(2004);
         }
      } else {
         switch (metaTypeCode) {
            case 0:
            case 16:
               return this.getPreferredType(-7);
            case 1:
            case 17:
               return this.getPreferredType(-6);
            case 2:
            case 18:
               if (this.storeCharsAsNumbers) {
                  return this.getPreferredType(4);
               }

               return this.getPreferredType(1);
            case 3:
            case 19:
               return this.getPreferredType(8);
            case 4:
            case 20:
               return this.getPreferredType(7);
            case 5:
            case 21:
               return this.getPreferredType(4);
            case 6:
            case 22:
               return this.getPreferredType(-5);
            case 7:
            case 23:
               return this.getPreferredType(5);
            case 9:
            case 26:
            case 1001:
            case 1005:
               return this.getPreferredType(12);
            case 10:
               if (this.storeLargeNumbersAsStrings) {
                  return this.getPreferredType(12);
               }

               return this.getPreferredType(2);
            case 14:
            case 28:
               return this.getPreferredType(93);
            case 24:
               if (this.storeLargeNumbersAsStrings) {
                  return this.getPreferredType(12);
               }

               return this.getPreferredType(8);
            case 25:
               if (this.storeLargeNumbersAsStrings) {
                  return this.getPreferredType(12);
               }

               return this.getPreferredType(-5);
            case 1000:
               return this.getPreferredType(2003);
            case 1002:
            case 1003:
            case 1004:
               return this.getPreferredType(2004);
            case 1006:
               return this.getPreferredType(2005);
            case 1007:
               return this.getPreferredType(91);
            case 1010:
               return this.getPreferredType(92);
            case 1011:
               return this.getPreferredType(93);
            default:
               return this.getPreferredType(2004);
         }
      }
   }

   public int getPreferredType(int type) {
      return type;
   }

   public String getTypeName(Column col) {
      if (!StringUtils.isEmpty(col.getTypeName())) {
         return this.appendSize(col, col.getTypeName());
      } else {
         return col.isAutoAssigned() && this.autoAssignTypeName != null ? this.appendSize(col, this.autoAssignTypeName) : this.appendSize(col, this.getTypeName(col.getType()));
      }
   }

   public String getTypeName(int type) {
      switch (type) {
         case -7:
            return this.bitTypeName;
         case -6:
            return this.tinyintTypeName;
         case -5:
            return this.bigintTypeName;
         case -4:
            return this.longVarbinaryTypeName;
         case -3:
            return this.varbinaryTypeName;
         case -2:
            return this.binaryTypeName;
         case -1:
            return this.longVarcharTypeName;
         case 0:
            return this.nullTypeName;
         case 1:
            return this.charTypeName;
         case 2:
            return this.numericTypeName;
         case 3:
            return this.decimalTypeName;
         case 4:
            return this.integerTypeName;
         case 5:
            return this.smallintTypeName;
         case 6:
            return this.floatTypeName;
         case 7:
            return this.realTypeName;
         case 8:
            return this.doubleTypeName;
         case 12:
            return this.varcharTypeName;
         case 16:
            return this.booleanTypeName;
         case 91:
            return this.dateTypeName;
         case 92:
            return this.timeTypeName;
         case 93:
            return this.timestampTypeName;
         case 1111:
            return this.otherTypeName;
         case 2000:
            return this.javaObjectTypeName;
         case 2001:
            return this.distinctTypeName;
         case 2002:
            return this.structTypeName;
         case 2003:
            return this.arrayTypeName;
         case 2004:
            return this.blobTypeName;
         case 2005:
            return this.clobTypeName;
         case 2006:
            return this.refTypeName;
         default:
            return this.otherTypeName;
      }
   }

   protected String appendSize(Column col, String typeName) {
      if (this.fixedSizeTypeNameSet.contains(typeName.toUpperCase())) {
         return typeName;
      } else if (typeName.indexOf(40) != -1) {
         return typeName;
      } else {
         String size = null;
         if (col.getSize() > 0) {
            StringBuffer buf = new StringBuffer(10);
            buf.append("(").append(col.getSize());
            if (col.getDecimalDigits() > 0) {
               buf.append(", ").append(col.getDecimalDigits());
            }

            buf.append(")");
            size = buf.toString();
         }

         return this.insertSize(typeName, size);
      }
   }

   protected String insertSize(String typeName, String size) {
      int idx;
      if (StringUtils.isEmpty(size)) {
         idx = typeName.indexOf("{0}");
         return idx != -1 ? typeName.substring(0, idx) : typeName;
      } else {
         idx = typeName.indexOf("{0}");
         String s;
         if (idx != -1) {
            s = typeName.substring(0, idx);
            if (size != null) {
               s = s + size;
            }

            if (typeName.length() > idx + 3) {
               s = s + typeName.substring(idx + 3);
            }

            return s;
         } else {
            if (!this.typeModifierSet.isEmpty()) {
               idx = typeName.length();
               int curIdx = true;
               Iterator i = this.typeModifierSet.iterator();

               while(i.hasNext()) {
                  s = (String)i.next();
                  if (typeName.toUpperCase().indexOf(s) != -1) {
                     int curIdx = typeName.toUpperCase().indexOf(s);
                     if (curIdx != -1 && curIdx < idx) {
                        idx = curIdx;
                     }
                  }
               }

               if (idx != typeName.length()) {
                  String ret = typeName.substring(0, idx);
                  ret = ret + size;
                  ret = ret + ' ' + typeName.substring(idx);
                  return ret;
               }
            }

            return typeName + size;
         }
      }
   }

   public void setJoinSyntax(String syntax) {
      if ("sql92".equals(syntax)) {
         this.joinSyntax = 0;
      } else if ("traditional".equals(syntax)) {
         this.joinSyntax = 1;
      } else if ("database".equals(syntax)) {
         this.joinSyntax = 2;
      } else if (!StringUtils.isEmpty(syntax)) {
         throw new IllegalArgumentException(syntax);
      }

   }

   public String getPlaceholderValueString(Column col) {
      switch (col.getType()) {
         case -7:
         case -6:
         case -5:
         case 2:
         case 4:
         case 5:
            return "0";
         case -1:
         case 12:
         case 2005:
            return "''";
         case 1:
            return this.storeCharsAsNumbers ? "0" : "' '";
         case 3:
         case 6:
         case 7:
         case 8:
            return "0.0";
         case 91:
            return ZERO_DATE_STR;
         case 92:
            return ZERO_TIME_STR;
         case 93:
            return ZERO_TIMESTAMP_STR;
         default:
            return "NULL";
      }
   }

   public SQLBuffer toSelectCount(Select sel) {
      SQLBuffer selectSQL = new SQLBuffer(this);
      sel.addJoinClassConditions();
      SQLBuffer from;
      if (sel.getFromSelect() != null) {
         from = this.getFromSelect(sel, false);
      } else {
         from = this.getFrom(sel, false);
      }

      SQLBuffer where = this.getWhere(sel, false);
      if (sel.getGrouping() == null && sel.getStartIndex() == 0L && sel.getEndIndex() == Long.MAX_VALUE) {
         List aliases = !sel.isDistinct() ? Collections.EMPTY_LIST : sel.getIdentifierAliases();
         if (aliases.isEmpty()) {
            selectSQL.append("COUNT(*)");
            return this.toSelect(selectSQL, (JDBCFetchConfiguration)null, from, where, (SQLBuffer)null, (SQLBuffer)null, (SQLBuffer)null, false, false, 0L, Long.MAX_VALUE);
         }

         if (aliases.size() == 1) {
            selectSQL.append("COUNT(DISTINCT ").append(aliases.get(0).toString()).append(")");
            return this.toSelect(selectSQL, (JDBCFetchConfiguration)null, from, where, (SQLBuffer)null, (SQLBuffer)null, (SQLBuffer)null, false, false, 0L, Long.MAX_VALUE);
         }

         if (this.distinctCountColumnSeparator != null) {
            selectSQL.append("COUNT(DISTINCT ");

            for(int i = 0; i < aliases.size(); ++i) {
               if (i > 0) {
                  selectSQL.append(" ");
                  selectSQL.append(this.distinctCountColumnSeparator);
                  selectSQL.append(" ");
               }

               selectSQL.append(aliases.get(i).toString());
            }

            selectSQL.append(")");
            return this.toSelect(selectSQL, (JDBCFetchConfiguration)null, from, where, (SQLBuffer)null, (SQLBuffer)null, (SQLBuffer)null, false, false, 0L, Long.MAX_VALUE);
         }
      }

      this.assertSupport(this.supportsSubselect, "SupportsSubselect");
      SQLBuffer subSelect = this.getSelects(sel, true, false);
      SQLBuffer subFrom = from;
      from = new SQLBuffer(this);
      from.append("(");
      from.append(this.toSelect(subSelect, (JDBCFetchConfiguration)null, subFrom, where, sel.getGrouping(), sel.getHaving(), (SQLBuffer)null, sel.isDistinct(), false, sel.getStartIndex(), sel.getEndIndex(), true, sel));
      from.append(")");
      if (this.requiresAliasForSubselect) {
         from.append(" ").append("s");
      }

      selectSQL.append("COUNT(*)");
      return this.toSelect(selectSQL, (JDBCFetchConfiguration)null, from, (SQLBuffer)null, (SQLBuffer)null, (SQLBuffer)null, (SQLBuffer)null, false, false, 0L, Long.MAX_VALUE);
   }

   public SQLBuffer toDelete(ClassMapping mapping, Select sel, Object[] params) {
      return this.toBulkOperation(mapping, sel, (JDBCStore)null, params, (Map)null);
   }

   public SQLBuffer toUpdate(ClassMapping mapping, Select sel, JDBCStore store, Object[] params, Map updates) {
      return this.toBulkOperation(mapping, sel, store, params, updates);
   }

   protected SQLBuffer toBulkOperation(ClassMapping mapping, Select sel, JDBCStore store, Object[] params, Map updateParams) {
      SQLBuffer sql = new SQLBuffer(this);
      SQLBuffer from;
      if (updateParams == null) {
         if (this.requiresTargetForDelete) {
            sql.append("DELETE ");
            from = this.getDeleteTargets(sel);
            sql.append(from);
            sql.append(" FROM ");
         } else {
            sql.append("DELETE FROM ");
         }
      } else {
         sql.append("UPDATE ");
      }

      sel.addJoinClassConditions();
      if (sel.getTableAliases().size() == 1 && this.supportsSubselect && this.allowsAliasInBulkClause) {
         if (sel.getFromSelect() != null) {
            from = this.getFromSelect(sel, false);
         } else {
            from = this.getFrom(sel, false);
         }

         sql.append(from);
         this.appendUpdates(sel, store, sql, params, updateParams, this.allowsAliasInBulkClause);
         SQLBuffer where = sel.getWhere();
         if (where != null && !where.isEmpty()) {
            sql.append(" WHERE ");
            sql.append(where);
         }

         return sql;
      } else {
         Table table = mapping.getTable();
         String tableName = this.getFullName(table, false);
         if (sel.getWhere() != null && !sel.getWhere().isEmpty()) {
            if (this.supportsSubselect && this.supportsCorrelatedSubselect) {
               Column[] pks = mapping.getPrimaryKeyColumns();
               sel.clearSelects();
               sel.setDistinct(true);
               if (pks.length == 1) {
                  sel.select(pks[0]);
                  sql.append(tableName);
                  this.appendUpdates(sel, store, sql, params, updateParams, false);
                  sql.append(" WHERE ").append(pks[0]).append(" IN (").append(sel.toSelect(false, (JDBCFetchConfiguration)null)).append(")");
               } else {
                  sel.clearSelects();
                  sel.setDistinct(false);
                  sel.select((String)"1", (Object)null);
                  Column[] cols = table.getPrimaryKey().getColumns();
                  SQLBuffer buf = new SQLBuffer(this);
                  buf.append("(");

                  for(int i = 0; i < cols.length; ++i) {
                     if (i > 0) {
                        buf.append(" AND ");
                     }

                     buf.append(sel.getColumnAlias((Column)cols[i], (Joins)null)).append(" = ").append(table).append(this.catalogSeparator).append(cols[i]);
                  }

                  buf.append(")");
                  sel.where((SQLBuffer)buf, (Joins)null);
                  sql.append(tableName);
                  this.appendUpdates(sel, store, sql, params, updateParams, false);
                  sql.append(" WHERE EXISTS (").append(sel.toSelect(false, (JDBCFetchConfiguration)null)).append(")");
               }

               return sql;
            } else {
               return null;
            }
         } else {
            sql.append(tableName);
            this.appendUpdates(sel, store, sql, params, updateParams, false);
            return sql;
         }
      }
   }

   protected SQLBuffer getDeleteTargets(Select sel) {
      SQLBuffer deleteTargets = new SQLBuffer(this);
      Collection aliases = sel.getTableAliases();
      Iterator itr = aliases.iterator();

      while(itr.hasNext()) {
         String tableAlias = itr.next().toString();
         int spaceIndex = tableAlias.indexOf(32);
         if (spaceIndex > 0 && spaceIndex < tableAlias.length() - 1) {
            if (this.allowsAliasInBulkClause) {
               deleteTargets.append(tableAlias.substring(spaceIndex + 1));
            } else {
               deleteTargets.append(tableAlias.substring(0, spaceIndex));
            }
         } else {
            deleteTargets.append(tableAlias);
         }

         if (itr.hasNext()) {
            deleteTargets.append(", ");
         }
      }

      return deleteTargets;
   }

   protected void appendUpdates(Select sel, JDBCStore store, SQLBuffer sql, Object[] params, Map updateParams, boolean allowAlias) {
      if (updateParams != null && updateParams.size() != 0) {
         sql.append(" SET ");
         ExpContext ctx = new ExpContext(store, params, store.getFetchConfiguration());
         boolean augmentUpdates = true;
         Iterator i = updateParams.entrySet().iterator();

         while(i.hasNext()) {
            Map.Entry next = (Map.Entry)i.next();
            Path path = (Path)next.getKey();
            FieldMapping fmd = (FieldMapping)path.last();
            if (fmd.isVersion()) {
               augmentUpdates = false;
            }

            Val val = (Val)next.getValue();
            Column col = fmd.getColumns()[0];
            if (allowAlias) {
               sql.append(sel.getColumnAlias(col));
            } else {
               sql.append(col.getName());
            }

            sql.append(" = ");
            ExpState state = val.initialize(sel, ctx, 0);
            ExpState pathState = ((Val)path).initialize(sel, ctx, 0);
            this.calculateValue(val, sel, ctx, state, path, pathState);
            int length = val.length(sel, ctx, state);

            for(int j = 0; j < length; ++j) {
               val.appendTo(allowAlias ? sel : null, ctx, state, sql, j);
            }

            if (i.hasNext()) {
               sql.append(", ");
            }
         }

         if (augmentUpdates) {
            Path path = (Path)updateParams.keySet().iterator().next();
            FieldMapping fm = (FieldMapping)path.last();
            ClassMapping meta = fm.getDeclaringMapping();
            Map updates = meta.getVersion().getBulkUpdateValues();
            Iterator iter = updates.entrySet().iterator();

            while(iter.hasNext()) {
               Map.Entry e = (Map.Entry)iter.next();
               Column col = (Column)e.getKey();
               String val = (String)e.getValue();
               sql.append(", ").append(col.getName()).append(" = ").append(val);
            }
         }

      }
   }

   public String[] getDeleteTableContentsSQL(Table[] tables) {
      Collection sql = new ArrayList();
      Collection deleteSQL = new ArrayList(tables.length);
      Collection restrictConstraints = new LinkedHashSet();

      for(int i = 0; i < tables.length; ++i) {
         ForeignKey[] fks = tables[i].getForeignKeys();

         for(int j = 0; j < fks.length; ++j) {
            if (!fks[j].isLogical() && !fks[j].isDeferred() && fks[j].getDeleteAction() == 2) {
               restrictConstraints.add(fks[j]);
            }

            String[] constraintSQL = this.getDropForeignKeySQL(fks[j]);
            sql.addAll(Arrays.asList(constraintSQL));
         }

         deleteSQL.add("DELETE FROM " + tables[i].getFullName());
      }

      sql.addAll(deleteSQL);
      Iterator iter = restrictConstraints.iterator();

      while(iter.hasNext()) {
         String[] constraintSQL = this.getAddForeignKeySQL((ForeignKey)iter.next());
         sql.addAll(Arrays.asList(constraintSQL));
      }

      return (String[])((String[])sql.toArray(new String[sql.size()]));
   }

   public SQLBuffer toSelect(Select sel, boolean forUpdate, JDBCFetchConfiguration fetch) {
      sel.addJoinClassConditions();
      boolean update = forUpdate && sel.getFromSelect() == null;
      SQLBuffer select = this.getSelects(sel, false, update);
      SQLBuffer ordering = null;
      if (!sel.isAggregate() || sel.getGrouping() != null) {
         ordering = sel.getOrdering();
      }

      SQLBuffer from;
      if (sel.getFromSelect() != null) {
         from = this.getFromSelect(sel, forUpdate);
      } else {
         from = this.getFrom(sel, update);
      }

      SQLBuffer where = this.getWhere(sel, update);
      return this.toSelect(select, fetch, from, where, sel.getGrouping(), sel.getHaving(), ordering, sel.isDistinct(), forUpdate, sel.getStartIndex(), sel.getEndIndex(), sel);
   }

   protected SQLBuffer getFrom(Select sel, boolean forUpdate) {
      SQLBuffer fromSQL = new SQLBuffer(this);
      Collection aliases = sel.getTableAliases();
      Iterator itr;
      if (aliases.size() >= 2 && sel.getJoinSyntax() == 0) {
         itr = sel.getJoinIterator();

         for(boolean first = true; itr.hasNext(); first = false) {
            fromSQL.append(this.toSQL92Join((Join)itr.next(), forUpdate, first));
         }
      } else {
         itr = aliases.iterator();

         while(itr.hasNext()) {
            fromSQL.append(itr.next().toString());
            if (forUpdate && this.tableForUpdateClause != null) {
               fromSQL.append(" ").append(this.tableForUpdateClause);
            }

            if (itr.hasNext()) {
               fromSQL.append(", ");
            }
         }
      }

      return fromSQL;
   }

   protected SQLBuffer getFromSelect(Select sel, boolean forUpdate) {
      SQLBuffer fromSQL = new SQLBuffer(this);
      fromSQL.append("(");
      fromSQL.append(this.toSelect(sel.getFromSelect(), forUpdate, (JDBCFetchConfiguration)null));
      fromSQL.append(")");
      if (this.requiresAliasForSubselect) {
         fromSQL.append(" ").append("s");
      }

      return fromSQL;
   }

   protected SQLBuffer getWhere(Select sel, boolean forUpdate) {
      Joins joins = sel.getJoins();
      if (sel.getJoinSyntax() != 0 && joins != null && !joins.isEmpty()) {
         SQLBuffer where = new SQLBuffer(this);
         if (sel.getWhere() != null) {
            where.append(sel.getWhere());
         }

         if (joins != null) {
            sel.append(where, joins);
         }

         return where;
      } else {
         return sel.getWhere();
      }
   }

   public SQLBuffer toTraditionalJoin(Join join) {
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
            ++i;
         }

         Column[] constCols = fk.getConstantColumns();

         for(int i = 0; i < constCols.length; ++count) {
            if (count > 0) {
               buf.append(" AND ");
            }

            if (inverse) {
               buf.appendValue(fk.getConstant(constCols[i]), constCols[i]);
            } else {
               buf.append(join.getAlias1()).append(".").append(constCols[i]);
            }

            buf.append(" = ");
            if (inverse) {
               buf.append(join.getAlias2()).append(".").append(constCols[i]);
            } else {
               buf.appendValue(fk.getConstant(constCols[i]), constCols[i]);
            }

            ++i;
         }

         Column[] constColsPK = fk.getConstantPrimaryKeyColumns();

         for(int i = 0; i < constColsPK.length; ++count) {
            if (count > 0) {
               buf.append(" AND ");
            }

            if (inverse) {
               buf.append(join.getAlias1()).append(".").append(constColsPK[i]);
            } else {
               buf.appendValue(fk.getPrimaryKeyConstant(constColsPK[i]), constColsPK[i]);
            }

            buf.append(" = ");
            if (inverse) {
               buf.appendValue(fk.getPrimaryKeyConstant(constColsPK[i]), constColsPK[i]);
            } else {
               buf.append(join.getAlias2()).append(".").append(constColsPK[i]);
            }

            ++i;
         }

         return buf;
      }
   }

   public SQLBuffer toSQL92Join(Join join, boolean forUpdate, boolean first) {
      SQLBuffer buf = new SQLBuffer(this);
      if (first) {
         buf.append(join.getTable1()).append(" ").append(join.getAlias1());
         if (forUpdate && this.tableForUpdateClause != null) {
            buf.append(" ").append(this.tableForUpdateClause);
         }
      }

      buf.append(" ");
      if (join.getType() == 1) {
         buf.append(this.outerJoinClause);
      } else if (join.getType() == 0) {
         buf.append(this.innerJoinClause);
      } else {
         buf.append(this.crossJoinClause);
      }

      buf.append(" ");
      buf.append(join.getTable2()).append(" ").append(join.getAlias2());
      if (forUpdate && this.tableForUpdateClause != null) {
         buf.append(" ").append(this.tableForUpdateClause);
      }

      if (join.getForeignKey() != null) {
         buf.append(" ON ").append(this.toTraditionalJoin(join));
      } else if (this.requiresConditionForCrossJoin && join.getType() == 2) {
         buf.append(" ON (1 = 1)");
      }

      return buf;
   }

   public SQLBuffer toNativeJoin(Join join) {
      throw new UnsupportedException();
   }

   public boolean canOuterJoin(int syntax, ForeignKey fk) {
      return syntax != 1;
   }

   public SQLBuffer toSelect(SQLBuffer selects, JDBCFetchConfiguration fetch, SQLBuffer from, SQLBuffer where, SQLBuffer group, SQLBuffer having, SQLBuffer order, boolean distinct, boolean forUpdate, long start, long end) {
      return this.toOperation(this.getSelectOperation(fetch), selects, from, where, group, having, order, distinct, start, end, this.getForUpdateClause(fetch, forUpdate, (Select)null));
   }

   protected SQLBuffer toSelect(SQLBuffer selects, JDBCFetchConfiguration fetch, SQLBuffer from, SQLBuffer where, SQLBuffer group, SQLBuffer having, SQLBuffer order, boolean distinct, boolean forUpdate, long start, long end, boolean subselect, Select sel) {
      return this.toOperation(this.getSelectOperation(fetch), selects, from, where, group, having, order, distinct, start, end, this.getForUpdateClause(fetch, forUpdate, (Select)null), subselect);
   }

   public SQLBuffer toSelect(SQLBuffer selects, JDBCFetchConfiguration fetch, SQLBuffer from, SQLBuffer where, SQLBuffer group, SQLBuffer having, SQLBuffer order, boolean distinct, boolean forUpdate, long start, long end, boolean subselect, boolean checkTableForUpdate) {
      return this.toOperation(this.getSelectOperation(fetch), selects, from, where, group, having, order, distinct, start, end, this.getForUpdateClause(fetch, forUpdate, (Select)null), subselect, checkTableForUpdate);
   }

   protected SQLBuffer toSelect(SQLBuffer selects, JDBCFetchConfiguration fetch, SQLBuffer from, SQLBuffer where, SQLBuffer group, SQLBuffer having, SQLBuffer order, boolean distinct, boolean forUpdate, long start, long end, Select sel) {
      return this.toOperation(this.getSelectOperation(fetch), selects, from, where, group, having, order, distinct, start, end, this.getForUpdateClause(fetch, forUpdate, sel));
   }

   protected String getForUpdateClause(JDBCFetchConfiguration fetch, boolean isForUpdate, Select sel) {
      if (fetch != null && fetch.getIsolation() != -1) {
         throw new InvalidStateException(_loc.get("isolation-level-config-not-supported", (Object)this.getClass().getName()));
      } else if (isForUpdate && !this.simulateLocking) {
         this.assertSupport(this.supportsSelectForUpdate, "SupportsSelectForUpdate");
         return this.forUpdateClause;
      } else {
         return null;
      }
   }

   public String getSelectOperation(JDBCFetchConfiguration fetch) {
      return "SELECT";
   }

   public SQLBuffer toOperation(String op, SQLBuffer selects, SQLBuffer from, SQLBuffer where, SQLBuffer group, SQLBuffer having, SQLBuffer order, boolean distinct, long start, long end, String forUpdateClause) {
      return this.toOperation(op, selects, from, where, group, having, order, distinct, start, end, forUpdateClause, false);
   }

   public SQLBuffer toOperation(String op, SQLBuffer selects, SQLBuffer from, SQLBuffer where, SQLBuffer group, SQLBuffer having, SQLBuffer order, boolean distinct, long start, long end, String forUpdateClause, boolean subselect) {
      return this.toOperation(op, selects, from, where, group, having, order, distinct, start, end, forUpdateClause, subselect, false);
   }

   private SQLBuffer toOperation(String op, SQLBuffer selects, SQLBuffer from, SQLBuffer where, SQLBuffer group, SQLBuffer having, SQLBuffer order, boolean distinct, long start, long end, String forUpdateClause, boolean subselect, boolean checkTableForUpdate) {
      SQLBuffer buf = new SQLBuffer(this);
      buf.append(op);
      boolean range = start != 0L || end != Long.MAX_VALUE;
      if (range && this.rangePosition == 1) {
         this.appendSelectRange(buf, start, end, subselect);
      }

      if (distinct) {
         buf.append(" DISTINCT");
      }

      if (range && this.rangePosition == 2) {
         this.appendSelectRange(buf, start, end, subselect);
      }

      buf.append(" ").append(selects).append(" FROM ").append(from);
      if (checkTableForUpdate && StringUtils.isEmpty(forUpdateClause) && !StringUtils.isEmpty(this.tableForUpdateClause)) {
         buf.append(" ").append(this.tableForUpdateClause);
      }

      if (where != null && !where.isEmpty()) {
         buf.append(" WHERE ").append(where);
      }

      if (group != null && !group.isEmpty()) {
         buf.append(" GROUP BY ").append(group);
      }

      if (having != null && !having.isEmpty()) {
         this.assertSupport(this.supportsHaving, "SupportsHaving");
         buf.append(" HAVING ").append(having);
      }

      if (order != null && !order.isEmpty()) {
         buf.append(" ORDER BY ").append(order);
      }

      if (range && this.rangePosition == 0) {
         this.appendSelectRange(buf, start, end, subselect);
      }

      if (forUpdateClause != null) {
         buf.append(" ").append(forUpdateClause);
      }

      if (range && this.rangePosition == 3) {
         this.appendSelectRange(buf, start, end, subselect);
      }

      return buf;
   }

   protected void appendSelectRange(SQLBuffer buf, long start, long end, boolean subselect) {
   }

   protected SQLBuffer getSelects(Select sel, boolean distinctIdentifiers, boolean forUpdate) {
      SQLBuffer selectSQL = new SQLBuffer(this);
      List aliases;
      if (distinctIdentifiers) {
         aliases = sel.getIdentifierAliases();
      } else {
         aliases = sel.getSelectAliases();
      }

      for(int i = 0; i < aliases.size(); ++i) {
         Object alias = aliases.get(i);
         this.appendSelect(selectSQL, alias, sel, i);
         if (i < aliases.size() - 1) {
            selectSQL.append(", ");
         }
      }

      return selectSQL;
   }

   protected void appendSelect(SQLBuffer selectSQL, Object elem, Select sel, int idx) {
      if (elem instanceof SQLBuffer) {
         selectSQL.append((SQLBuffer)elem);
      } else {
         selectSQL.append(elem.toString());
      }

   }

   public boolean supportsLocking(Select sel) {
      if (sel.isAggregate()) {
         return false;
      } else if (!this.supportsSelectForUpdate) {
         return false;
      } else if (this.supportsLockingWithSelectRange || sel.getStartIndex() == 0L && sel.getEndIndex() == Long.MAX_VALUE) {
         if (sel.getFromSelect() != null) {
            sel = sel.getFromSelect();
         }

         if (!this.supportsLockingWithDistinctClause && sel.isDistinct()) {
            return false;
         } else if (!this.supportsLockingWithMultipleTables && sel.getTableAliases().size() > 1) {
            return false;
         } else if (!this.supportsLockingWithOrderClause && sel.getOrdering() != null) {
            return false;
         } else {
            if (!this.supportsLockingWithOuterJoin || !this.supportsLockingWithInnerJoin) {
               Iterator itr = sel.getJoinIterator();

               while(itr.hasNext()) {
                  Join join = (Join)itr.next();
                  if (!this.supportsLockingWithOuterJoin && join.getType() == 1) {
                     return false;
                  }

                  if (!this.supportsLockingWithInnerJoin && join.getType() == 0) {
                     return false;
                  }
               }
            }

            return true;
         }
      } else {
         return false;
      }
   }

   public boolean supportsRandomAccessResultSet(Select sel, boolean forUpdate) {
      return !sel.isAggregate();
   }

   public void assertSupport(boolean feature, String property) {
      if (!feature) {
         throw new UnsupportedException(_loc.get("feature-not-supported", this.getClass(), property));
      }
   }

   public void substring(SQLBuffer buf, FilterValue str, FilterValue start, FilterValue end) {
      buf.append(this.substringFunctionName).append("(");
      str.appendTo(buf);
      buf.append(", ");
      long startLong;
      if (start.getValue() instanceof Number) {
         startLong = this.toLong(start);
         buf.append(Long.toString(startLong + 1L));
      } else {
         buf.append("(");
         start.appendTo(buf);
         buf.append(" + 1)");
      }

      if (end != null) {
         buf.append(", ");
         if (start.getValue() instanceof Number && end.getValue() instanceof Number) {
            startLong = this.toLong(start);
            long endLong = this.toLong(end);
            buf.append(Long.toString(endLong - startLong));
         } else {
            end.appendTo(buf);
            buf.append(" - (");
            start.appendTo(buf);
            buf.append(")");
         }
      }

      buf.append(")");
   }

   long toLong(FilterValue litValue) {
      return ((Number)litValue.getValue()).longValue();
   }

   public void indexOf(SQLBuffer buf, FilterValue str, FilterValue find, FilterValue start) {
      buf.append("(INSTR((");
      if (start != null) {
         this.substring(buf, str, start, (FilterValue)null);
      } else {
         str.appendTo(buf);
      }

      buf.append("), (");
      find.appendTo(buf);
      buf.append(")) - 1");
      if (start != null) {
         buf.append(" + ");
         start.appendTo(buf);
      }

      buf.append(")");
   }

   public void mathFunction(SQLBuffer buf, String op, FilterValue lhs, FilterValue rhs) {
      boolean castlhs = false;
      boolean castrhs = false;
      Class lc = Filters.wrap(lhs.getType());
      Class rc = Filters.wrap(rhs.getType());
      int type = 0;
      if (this.requiresCastForMathFunctions && (lc != rc || lhs.isConstant() && rhs.isConstant())) {
         Class c = Filters.promote(lc, rc);
         type = this.getJDBCType(JavaTypes.getTypeCode(c), false);
         if (type != -3 && type != 2004) {
            castlhs = lhs.isConstant() && rhs.isConstant() || lc != c;
            castrhs = lhs.isConstant() && rhs.isConstant() || rc != c;
         }
      }

      boolean mod = "MOD".equals(op);
      if (mod) {
         if (this.supportsModOperator) {
            op = "%";
         } else {
            buf.append(op);
         }
      }

      buf.append("(");
      if (castlhs) {
         this.appendCast(buf, lhs, type);
      } else {
         lhs.appendTo(buf);
      }

      if (mod && !this.supportsModOperator) {
         buf.append(", ");
      } else {
         buf.append(" ").append(op).append(" ");
      }

      if (castrhs) {
         this.appendCast(buf, rhs, type);
      } else {
         rhs.appendTo(buf);
      }

      buf.append(")");
   }

   public void comparison(SQLBuffer buf, String op, FilterValue lhs, FilterValue rhs) {
      boolean lhsxml = lhs.getXPath() != null;
      boolean rhsxml = rhs.getXPath() != null;
      if (!lhsxml && !rhsxml) {
         boolean castlhs = false;
         boolean castrhs = false;
         Class lc = Filters.wrap(lhs.getType());
         Class rc = Filters.wrap(rhs.getType());
         int type = 0;
         if (this.requiresCastForComparisons && (lc != rc || lhs.isConstant() && rhs.isConstant())) {
            Class c = Filters.promote(lc, rc);
            type = this.getJDBCType(JavaTypes.getTypeCode(c), false);
            if (type != -3 && type != 2004) {
               castlhs = lhs.isConstant() && rhs.isConstant() || lc != c;
               castrhs = lhs.isConstant() && rhs.isConstant() || rc != c;
            }
         }

         if (castlhs) {
            this.appendCast(buf, lhs, type);
         } else {
            lhs.appendTo(buf);
         }

         buf.append(" ").append(op).append(" ");
         if (castrhs) {
            this.appendCast(buf, rhs, type);
         } else {
            rhs.appendTo(buf);
         }

      } else {
         this.appendXmlComparison(buf, op, lhs, rhs, lhsxml, rhsxml);
      }
   }

   public void appendXmlComparison(SQLBuffer buf, String op, FilterValue lhs, FilterValue rhs, boolean lhsxml, boolean rhsxml) {
      this.assertSupport(this.supportsXMLColumn, "SupportsXMLColumn");
   }

   protected void appendNumericCast(SQLBuffer buf, FilterValue val) {
      if (val.isConstant()) {
         this.appendCast(buf, val, 2);
      } else {
         val.appendTo(buf);
      }

   }

   public void appendCast(SQLBuffer buf, Object val, int type) {
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

      buf.append(pre);
      if (val instanceof FilterValue) {
         ((FilterValue)val).appendTo(buf);
      } else if (val instanceof SQLBuffer) {
         buf.append((SQLBuffer)val);
      } else {
         buf.append(val.toString());
      }

      buf.append(mid);
      buf.append(this.getTypeName(type));
      this.appendLength(buf, type);
      buf.append(post);
   }

   protected void appendLength(SQLBuffer buf, int type) {
   }

   public String addCastAsType(String func, Val val) {
      return null;
   }

   public void refSchemaComponents(Table table) {
   }

   public String getFullName(Table table, boolean logical) {
      if (this.useSchemaName && table.getSchemaName() != null) {
         return !logical && !".".equals(this.catalogSeparator) ? table.getSchemaName() + this.catalogSeparator + table.getName() : table.getFullName();
      } else {
         return table.getName();
      }
   }

   public String getFullName(Index index) {
      if (this.useSchemaName && index.getSchemaName() != null) {
         return ".".equals(this.catalogSeparator) ? index.getFullName() : index.getSchemaName() + this.catalogSeparator + index.getName();
      } else {
         return index.getName();
      }
   }

   public String getFullName(Sequence seq) {
      if (this.useSchemaName && seq.getSchemaName() != null) {
         return ".".equals(this.catalogSeparator) ? seq.getFullName() : seq.getSchemaName() + this.catalogSeparator + seq.getName();
      } else {
         return seq.getName();
      }
   }

   public String getValidTableName(String name, Schema schema) {
      while(name.startsWith("_")) {
         name = name.substring(1);
      }

      return this.makeNameValid(name, schema.getSchemaGroup(), this.maxTableNameLength, 1);
   }

   public String getValidSequenceName(String name, Schema schema) {
      while(name.startsWith("_")) {
         name = name.substring(1);
      }

      return this.makeNameValid("S_" + name, schema.getSchemaGroup(), this.maxTableNameLength, 2);
   }

   public String getValidColumnName(String name, Table table) {
      return this.getValidColumnName(name, table, true);
   }

   public String getValidColumnName(String name, Table table, boolean checkForUniqueness) {
      while(name.startsWith("_")) {
         name = name.substring(1);
      }

      return this.makeNameValid(name, table, this.maxColumnNameLength, 0, checkForUniqueness);
   }

   public String getValidPrimaryKeyName(String name, Table table) {
      while(name.startsWith("_")) {
         name = name.substring(1);
      }

      return this.makeNameValid("P_" + name, table.getSchema().getSchemaGroup(), this.maxConstraintNameLength, 0);
   }

   public String getValidForeignKeyName(String name, Table table, Table toTable) {
      while(name.startsWith("_")) {
         name = name.substring(1);
      }

      String tableName = table.getName();
      int len = Math.min(tableName.length(), 7);
      name = "F_" + shorten(tableName, len) + "_" + name;
      return this.makeNameValid(name, table.getSchema().getSchemaGroup(), this.maxConstraintNameLength, 0);
   }

   public String getValidIndexName(String name, Table table) {
      while(name.startsWith("_")) {
         name = name.substring(1);
      }

      String tableName = table.getName();
      int len = Math.min(tableName.length(), 7);
      name = "I_" + shorten(tableName, len) + "_" + name;
      return this.makeNameValid(name, table.getSchema().getSchemaGroup(), this.maxIndexNameLength, 0);
   }

   public String getValidUniqueName(String name, Table table) {
      while(name.startsWith("_")) {
         name = name.substring(1);
      }

      String tableName = table.getName();
      int len = Math.min(tableName.length(), 7);
      name = "U_" + shorten(tableName, len) + "_" + name;
      return this.makeNameValid(name, table.getSchema().getSchemaGroup(), this.maxConstraintNameLength, 0);
   }

   protected static String shorten(String name, int targetLength) {
      if (name != null && name.length() > targetLength) {
         StringBuffer nm = new StringBuffer(name);

         while(nm.length() > targetLength) {
            if (!stripVowel(nm)) {
               nm.replace(nm.length() / 2, nm.length() / 2 + 1, "");
            }
         }

         return nm.toString();
      } else {
         return name;
      }
   }

   private static boolean stripVowel(StringBuffer name) {
      if (name != null && name.length() != 0) {
         char[] vowels = new char[]{'A', 'E', 'I', 'O', 'U'};

         for(int i = 0; i < vowels.length; ++i) {
            int index = name.toString().toUpperCase().indexOf(vowels[i]);
            if (index != -1) {
               name.replace(index, index + 1, "");
               return true;
            }
         }

         return false;
      } else {
         return false;
      }
   }

   protected String makeNameValid(String name, NameSet set, int maxLen, int nameType) {
      return this.makeNameValid(name, set, maxLen, nameType, true);
   }

   protected String makeNameValid(String name, NameSet set, int maxLen, int nameType, boolean checkForUniqueness) {
      if (maxLen < 1) {
         maxLen = 255;
      }

      if (name.length() > maxLen) {
         name = name.substring(0, maxLen);
      }

      if (this.reservedWordSet.contains(name.toUpperCase())) {
         if (name.length() == maxLen) {
            name = name.substring(0, name.length() - 1);
         }

         name = name + "0";
      }

      if (set != null && checkForUniqueness) {
         int version = 1;
         int chars = 1;

         while(true) {
            switch (nameType) {
               case 1:
                  if (!((SchemaGroup)set).isKnownTable(name)) {
                     return name.toUpperCase();
                  }
                  break;
               case 2:
                  if (!((SchemaGroup)set).isKnownSequence(name)) {
                     return name.toUpperCase();
                  }
                  break;
               default:
                  if (!set.isNameTaken(name)) {
                     return name.toUpperCase();
                  }
            }

            if (version > 1) {
               name = name.substring(0, name.length() - chars);
            }

            if ((double)version >= Math.pow(10.0, (double)chars)) {
               ++chars;
            }

            if (name.length() + chars > maxLen) {
               name = name.substring(0, maxLen - chars);
            }

            name = name + version;
            ++version;
         }
      } else {
         return name.toUpperCase();
      }
   }

   public String[] getCreateTableSQL(Table table) {
      StringBuffer buf = new StringBuffer();
      buf.append("CREATE TABLE ").append(this.getFullName(table, false));
      if (this.supportsComments && table.hasComment()) {
         buf.append(" ");
         this.comment(buf, table.getComment());
         buf.append("\n    (");
      } else {
         buf.append(" (");
      }

      StringBuffer endBuf = new StringBuffer();
      PrimaryKey pk = table.getPrimaryKey();
      if (pk != null) {
         String pkStr = this.getPrimaryKeyConstraintSQL(pk);
         if (pkStr != null) {
            endBuf.append(pkStr);
         }
      }

      Unique[] unqs = table.getUniques();

      for(int i = 0; i < unqs.length; ++i) {
         String unqStr = this.getUniqueConstraintSQL(unqs[i]);
         if (unqStr != null) {
            if (endBuf.length() > 0) {
               endBuf.append(", ");
            }

            endBuf.append(unqStr);
         }
      }

      Column[] cols = table.getColumns();

      for(int i = 0; i < cols.length; ++i) {
         buf.append(this.getDeclareColumnSQL(cols[i], false));
         if (i < cols.length - 1 || endBuf.length() > 0) {
            buf.append(", ");
         }

         if (this.supportsComments && cols[i].hasComment()) {
            this.comment(buf, cols[i].getComment());
            buf.append("\n    ");
         }
      }

      buf.append(endBuf.toString());
      buf.append(")");
      return new String[]{buf.toString()};
   }

   protected StringBuffer comment(StringBuffer buf, String comment) {
      return buf.append("-- ").append(comment);
   }

   public String[] getDropTableSQL(Table table) {
      String drop = MessageFormat.format(this.dropTableSQL, this.getFullName(table, false));
      return new String[]{drop};
   }

   public String[] getCreateSequenceSQL(Sequence seq) {
      if (this.nextSequenceQuery == null) {
         return null;
      } else {
         StringBuffer buf = new StringBuffer();
         buf.append("CREATE SEQUENCE ");
         buf.append(this.getFullName(seq));
         if (seq.getInitialValue() != 0) {
            buf.append(" START WITH ").append(seq.getInitialValue());
         }

         if (seq.getIncrement() > 1) {
            buf.append(" INCREMENT BY ").append(seq.getIncrement());
         }

         return new String[]{buf.toString()};
      }
   }

   public String[] getDropSequenceSQL(Sequence seq) {
      return new String[]{"DROP SEQUENCE " + this.getFullName(seq)};
   }

   public String[] getCreateIndexSQL(Index index) {
      StringBuffer buf = new StringBuffer();
      buf.append("CREATE ");
      if (index.isUnique()) {
         buf.append("UNIQUE ");
      }

      buf.append("INDEX ").append(index.getName());
      buf.append(" ON ").append(this.getFullName(index.getTable(), false));
      buf.append(" (").append(Strings.join(index.getColumns(), ", ")).append(")");
      return new String[]{buf.toString()};
   }

   public String[] getDropIndexSQL(Index index) {
      return new String[]{"DROP INDEX " + this.getFullName(index)};
   }

   public String[] getAddColumnSQL(Column column) {
      if (!this.supportsAlterTableWithAddColumn) {
         return new String[0];
      } else {
         String dec = this.getDeclareColumnSQL(column, true);
         return dec == null ? new String[0] : new String[]{"ALTER TABLE " + this.getFullName(column.getTable(), false) + " ADD " + dec};
      }
   }

   public String[] getDropColumnSQL(Column column) {
      return !this.supportsAlterTableWithDropColumn ? new String[0] : new String[]{"ALTER TABLE " + this.getFullName(column.getTable(), false) + " DROP COLUMN " + column};
   }

   public String[] getAddPrimaryKeySQL(PrimaryKey pk) {
      String pksql = this.getPrimaryKeyConstraintSQL(pk);
      return pksql == null ? new String[0] : new String[]{"ALTER TABLE " + this.getFullName(pk.getTable(), false) + " ADD " + pksql};
   }

   public String[] getDropPrimaryKeySQL(PrimaryKey pk) {
      return pk.getName() == null ? new String[0] : new String[]{"ALTER TABLE " + this.getFullName(pk.getTable(), false) + " DROP CONSTRAINT " + pk.getName()};
   }

   public String[] getAddForeignKeySQL(ForeignKey fk) {
      String fkSQL = this.getForeignKeyConstraintSQL(fk);
      return fkSQL == null ? new String[0] : new String[]{"ALTER TABLE " + this.getFullName(fk.getTable(), false) + " ADD " + fkSQL};
   }

   public String[] getDropForeignKeySQL(ForeignKey fk) {
      return fk.getName() == null ? new String[0] : new String[]{"ALTER TABLE " + this.getFullName(fk.getTable(), false) + " DROP CONSTRAINT " + fk.getName()};
   }

   protected String getDeclareColumnSQL(Column col, boolean alter) {
      StringBuffer buf = new StringBuffer();
      buf.append(col).append(" ");
      buf.append(this.getTypeName(col));
      if (!alter) {
         if (col.getDefaultString() != null && !col.isAutoAssigned()) {
            buf.append(" DEFAULT ").append(col.getDefaultString());
         }

         if (col.isNotNull()) {
            buf.append(" NOT NULL");
         }
      }

      if (col.isAutoAssigned()) {
         if (!this.supportsAutoAssign) {
            this.log.warn(_loc.get("invalid-autoassign", this.platform, col));
         } else if (this.autoAssignClause != null) {
            buf.append(" ").append(this.autoAssignClause);
         }
      }

      return buf.toString();
   }

   protected String getPrimaryKeyConstraintSQL(PrimaryKey pk) {
      if (!this.createPrimaryKeys) {
         return null;
      } else {
         String name = pk.getName();
         if (name != null && this.reservedWordSet.contains(name.toUpperCase())) {
            name = null;
         }

         StringBuffer buf = new StringBuffer();
         if (name != null && "before".equals(this.constraintNameMode)) {
            buf.append("CONSTRAINT ").append(name).append(" ");
         }

         buf.append("PRIMARY KEY ");
         if (name != null && "mid".equals(this.constraintNameMode)) {
            buf.append(name).append(" ");
         }

         buf.append("(").append(Strings.join(pk.getColumns(), ", ")).append(")");
         if (name != null && "after".equals(this.constraintNameMode)) {
            buf.append(" CONSTRAINT ").append(name);
         }

         return buf.toString();
      }
   }

   protected String getForeignKeyConstraintSQL(ForeignKey fk) {
      if (!this.supportsForeignKeys) {
         return null;
      } else if (fk.getDeleteAction() == 1) {
         return null;
      } else if (fk.isDeferred() && !this.supportsDeferredForeignKeyConstraints()) {
         return null;
      } else if (this.supportsDeleteAction(fk.getDeleteAction()) && this.supportsUpdateAction(fk.getUpdateAction())) {
         Column[] locals = fk.getColumns();
         Column[] foreigns = fk.getPrimaryKeyColumns();
         int delActionId = fk.getDeleteAction();
         if (delActionId == 4) {
            for(int i = 0; i < locals.length; ++i) {
               if (locals[i].isNotNull()) {
                  delActionId = 1;
               }
            }
         }

         String delAction = this.getActionName(delActionId);
         String upAction = this.getActionName(fk.getUpdateAction());
         StringBuffer buf = new StringBuffer();
         if (fk.getName() != null && "before".equals(this.constraintNameMode)) {
            buf.append("CONSTRAINT ").append(fk.getName()).append(" ");
         }

         buf.append("FOREIGN KEY ");
         if (fk.getName() != null && "mid".equals(this.constraintNameMode)) {
            buf.append(fk.getName()).append(" ");
         }

         buf.append("(").append(Strings.join(locals, ", ")).append(")");
         buf.append(" REFERENCES ");
         buf.append(this.getFullName(foreigns[0].getTable(), false));
         buf.append(" (").append(Strings.join(foreigns, ", ")).append(")");
         if (delAction != null) {
            buf.append(" ON DELETE ").append(delAction);
         }

         if (upAction != null) {
            buf.append(" ON UPDATE ").append(upAction);
         }

         if (fk.isDeferred()) {
            buf.append(" INITIALLY DEFERRED");
         }

         if (this.supportsDeferredForeignKeyConstraints()) {
            buf.append(" DEFERRABLE");
         }

         if (fk.getName() != null && "after".equals(this.constraintNameMode)) {
            buf.append(" CONSTRAINT ").append(fk.getName());
         }

         return buf.toString();
      } else {
         return null;
      }
   }

   protected boolean supportsDeferredForeignKeyConstraints() {
      return this.supportsDeferredConstraints;
   }

   private String getActionName(int action) {
      switch (action) {
         case 3:
            return "CASCADE";
         case 4:
            return "SET NULL";
         case 5:
            return "SET DEFAULT";
         default:
            return null;
      }
   }

   public boolean supportsDeleteAction(int action) {
      if (action == 1) {
         return true;
      } else if (!this.supportsForeignKeys) {
         return false;
      } else {
         switch (action) {
            case 2:
               return this.supportsRestrictDeleteAction;
            case 3:
               return this.supportsCascadeDeleteAction;
            case 4:
               return this.supportsNullDeleteAction;
            case 5:
               return this.supportsDefaultDeleteAction;
            default:
               return false;
         }
      }
   }

   public boolean supportsUpdateAction(int action) {
      if (action == 1) {
         return true;
      } else if (!this.supportsForeignKeys) {
         return false;
      } else {
         switch (action) {
            case 2:
               return this.supportsRestrictUpdateAction;
            case 3:
               return this.supportsCascadeUpdateAction;
            case 4:
               return this.supportsNullUpdateAction;
            case 5:
               return this.supportsDefaultUpdateAction;
            default:
               return false;
         }
      }
   }

   protected String getUniqueConstraintSQL(Unique unq) {
      if (!this.supportsUniqueConstraints || unq.isDeferred() && !this.supportsDeferredUniqueConstraints()) {
         return null;
      } else {
         StringBuffer buf = new StringBuffer();
         if (unq.getName() != null && "before".equals(this.constraintNameMode)) {
            buf.append("CONSTRAINT ").append(unq.getName()).append(" ");
         }

         buf.append("UNIQUE ");
         if (unq.getName() != null && "mid".equals(this.constraintNameMode)) {
            buf.append(unq.getName()).append(" ");
         }

         buf.append("(").append(Strings.join(unq.getColumns(), ", ")).append(")");
         if (unq.isDeferred()) {
            buf.append(" INITIALLY DEFERRED");
         }

         if (this.supportsDeferredUniqueConstraints()) {
            buf.append(" DEFERRABLE");
         }

         if (unq.getName() != null && "after".equals(this.constraintNameMode)) {
            buf.append(" CONSTRAINT ").append(unq.getName());
         }

         return buf.toString();
      }
   }

   protected boolean supportsDeferredUniqueConstraints() {
      return this.supportsDeferredConstraints;
   }

   public boolean isSystemTable(String name, String schema, boolean targetSchema) {
      if (this.systemTableSet.contains(name.toUpperCase())) {
         return true;
      } else {
         return !targetSchema && schema != null && this.systemSchemaSet.contains(schema.toUpperCase());
      }
   }

   public boolean isSystemIndex(String name, Table table) {
      return false;
   }

   public boolean isSystemSequence(String name, String schema, boolean targetSchema) {
      return !targetSchema && schema != null && this.systemSchemaSet.contains(schema.toUpperCase());
   }

   public Table[] getTables(DatabaseMetaData meta, String catalog, String schemaName, String tableName, Connection conn) throws SQLException {
      if (!this.supportsSchemaForGetTables) {
         schemaName = null;
      } else {
         schemaName = this.getSchemaNameForMetadata(schemaName);
      }

      String[] types = Strings.split(this.tableTypes, ",", 0);

      for(int i = 0; i < types.length; ++i) {
         types[i] = types[i].trim();
      }

      this.beforeMetadataOperation(conn);
      ResultSet tables = null;

      try {
         tables = meta.getTables(this.getCatalogNameForMetadata(catalog), schemaName, this.getTableNameForMetadata(tableName), types);
         List tableList = new ArrayList();

         while(tables != null && tables.next()) {
            tableList.add(this.newTable(tables));
         }

         Table[] var9 = (Table[])((Table[])tableList.toArray(new Table[tableList.size()]));
         return var9;
      } finally {
         if (tables != null) {
            try {
               tables.close();
            } catch (Exception var16) {
            }
         }

      }
   }

   protected Table newTable(ResultSet tableMeta) throws SQLException {
      Table t = new Table();
      t.setName(tableMeta.getString("TABLE_NAME"));
      return t;
   }

   public Sequence[] getSequences(DatabaseMetaData meta, String catalog, String schemaName, String sequenceName, Connection conn) throws SQLException {
      String str = this.getSequencesSQL(schemaName, sequenceName);
      if (str == null) {
         return new Sequence[0];
      } else {
         PreparedStatement stmnt = this.prepareStatement(conn, str);
         ResultSet rs = null;

         Sequence[] var10;
         try {
            int idx = 1;
            if (schemaName != null) {
               stmnt.setString(idx++, schemaName.toUpperCase());
            }

            if (sequenceName != null) {
               stmnt.setString(idx++, sequenceName);
            }

            rs = this.executeQuery(conn, stmnt, str);
            var10 = this.getSequence(rs);
         } finally {
            if (rs != null) {
               try {
                  rs.close();
               } catch (SQLException var20) {
               }
            }

            if (stmnt != null) {
               try {
                  stmnt.close();
               } catch (SQLException var19) {
               }
            }

         }

         return var10;
      }
   }

   protected Sequence newSequence(ResultSet sequenceMeta) throws SQLException {
      Sequence seq = new Sequence();
      seq.setSchemaName(sequenceMeta.getString("SEQUENCE_SCHEMA"));
      seq.setName(sequenceMeta.getString("SEQUENCE_NAME"));
      return seq;
   }

   protected String getSequencesSQL(String schemaName, String sequenceName) {
      return null;
   }

   public Column[] getColumns(DatabaseMetaData meta, String catalog, String schemaName, String tableName, String columnName, Connection conn) throws SQLException {
      if (tableName == null && !this.supportsNullTableForGetColumns) {
         return null;
      } else {
         if (!this.supportsSchemaForGetColumns) {
            schemaName = null;
         } else {
            schemaName = this.getSchemaNameForMetadata(schemaName);
         }

         this.beforeMetadataOperation(conn);
         ResultSet cols = null;

         try {
            cols = meta.getColumns(this.getCatalogNameForMetadata(catalog), schemaName, this.getTableNameForMetadata(tableName), this.getColumnNameForMetadata(columnName));
            List columnList = new ArrayList();

            while(cols != null && cols.next()) {
               columnList.add(this.newColumn(cols));
            }

            Column[] var9 = (Column[])((Column[])columnList.toArray(new Column[columnList.size()]));
            return var9;
         } finally {
            if (cols != null) {
               try {
                  cols.close();
               } catch (Exception var16) {
               }
            }

         }
      }
   }

   protected Column newColumn(ResultSet colMeta) throws SQLException {
      Column c = new Column();
      c.setSchemaName(colMeta.getString("TABLE_SCHEM"));
      c.setTableName(colMeta.getString("TABLE_NAME"));
      c.setName(colMeta.getString("COLUMN_NAME"));
      c.setType(colMeta.getInt("DATA_TYPE"));
      c.setTypeName(colMeta.getString("TYPE_NAME"));
      c.setSize(colMeta.getInt("COLUMN_SIZE"));
      c.setDecimalDigits(colMeta.getInt("DECIMAL_DIGITS"));
      c.setNotNull(colMeta.getInt("NULLABLE") == 0);
      String def = colMeta.getString("COLUMN_DEF");
      if (!StringUtils.isEmpty(def) && !"null".equalsIgnoreCase(def)) {
         c.setDefaultString(def);
      }

      return c;
   }

   public PrimaryKey[] getPrimaryKeys(DatabaseMetaData meta, String catalog, String schemaName, String tableName, Connection conn) throws SQLException {
      return this.useGetBestRowIdentifierForPrimaryKeys ? this.getPrimaryKeysFromBestRowIdentifier(meta, catalog, schemaName, tableName, conn) : this.getPrimaryKeysFromGetPrimaryKeys(meta, catalog, schemaName, tableName, conn);
   }

   protected PrimaryKey[] getPrimaryKeysFromGetPrimaryKeys(DatabaseMetaData meta, String catalog, String schemaName, String tableName, Connection conn) throws SQLException {
      if (tableName == null && !this.supportsNullTableForGetPrimaryKeys) {
         return null;
      } else {
         this.beforeMetadataOperation(conn);
         ResultSet pks = null;

         try {
            pks = meta.getPrimaryKeys(this.getCatalogNameForMetadata(catalog), this.getSchemaNameForMetadata(schemaName), this.getTableNameForMetadata(tableName));
            List pkList = new ArrayList();

            while(pks != null && pks.next()) {
               pkList.add(this.newPrimaryKey(pks));
            }

            PrimaryKey[] var8 = (PrimaryKey[])((PrimaryKey[])pkList.toArray(new PrimaryKey[pkList.size()]));
            return var8;
         } finally {
            if (pks != null) {
               try {
                  pks.close();
               } catch (Exception var15) {
               }
            }

         }
      }
   }

   protected PrimaryKey newPrimaryKey(ResultSet pkMeta) throws SQLException {
      PrimaryKey pk = new PrimaryKey();
      pk.setSchemaName(pkMeta.getString("TABLE_SCHEM"));
      pk.setTableName(pkMeta.getString("TABLE_NAME"));
      pk.setColumnName(pkMeta.getString("COLUMN_NAME"));
      pk.setName(pkMeta.getString("PK_NAME"));
      return pk;
   }

   protected PrimaryKey[] getPrimaryKeysFromBestRowIdentifier(DatabaseMetaData meta, String catalog, String schemaName, String tableName, Connection conn) throws SQLException {
      if (tableName == null) {
         return null;
      } else {
         this.beforeMetadataOperation(conn);
         ResultSet pks = null;

         try {
            pks = meta.getBestRowIdentifier(catalog, schemaName, tableName, 0, false);
            List pkList = new ArrayList();

            while(pks != null && pks.next()) {
               PrimaryKey pk = new PrimaryKey();
               pk.setSchemaName(schemaName);
               pk.setTableName(tableName);
               pk.setColumnName(pks.getString("COLUMN_NAME"));
               pkList.add(pk);
            }

            PrimaryKey[] var17 = (PrimaryKey[])((PrimaryKey[])pkList.toArray(new PrimaryKey[pkList.size()]));
            return var17;
         } finally {
            if (pks != null) {
               try {
                  pks.close();
               } catch (Exception var15) {
               }
            }

         }
      }
   }

   public Index[] getIndexInfo(DatabaseMetaData meta, String catalog, String schemaName, String tableName, boolean unique, boolean approx, Connection conn) throws SQLException {
      if (tableName == null && !this.supportsNullTableForGetIndexInfo) {
         return null;
      } else {
         this.beforeMetadataOperation(conn);
         ResultSet indexes = null;

         try {
            indexes = meta.getIndexInfo(this.getCatalogNameForMetadata(catalog), this.getSchemaNameForMetadata(schemaName), this.getTableNameForMetadata(tableName), unique, approx);
            List indexList = new ArrayList();

            while(indexes != null && indexes.next()) {
               indexList.add(this.newIndex(indexes));
            }

            Index[] var10 = (Index[])((Index[])indexList.toArray(new Index[indexList.size()]));
            return var10;
         } finally {
            if (indexes != null) {
               try {
                  indexes.close();
               } catch (Exception var17) {
               }
            }

         }
      }
   }

   protected Index newIndex(ResultSet idxMeta) throws SQLException {
      Index idx = new Index();
      idx.setSchemaName(idxMeta.getString("TABLE_SCHEM"));
      idx.setTableName(idxMeta.getString("TABLE_NAME"));
      idx.setColumnName(idxMeta.getString("COLUMN_NAME"));
      idx.setName(idxMeta.getString("INDEX_NAME"));
      idx.setUnique(!idxMeta.getBoolean("NON_UNIQUE"));
      return idx;
   }

   public ForeignKey[] getImportedKeys(DatabaseMetaData meta, String catalog, String schemaName, String tableName, Connection conn) throws SQLException {
      if (!this.supportsForeignKeys) {
         return null;
      } else if (tableName == null && !this.supportsNullTableForGetImportedKeys) {
         return null;
      } else {
         this.beforeMetadataOperation(conn);
         ResultSet keys = null;

         try {
            keys = meta.getImportedKeys(this.getCatalogNameForMetadata(catalog), this.getSchemaNameForMetadata(schemaName), this.getTableNameForMetadata(tableName));
            List importedKeyList = new ArrayList();

            while(keys != null && keys.next()) {
               importedKeyList.add(this.newForeignKey(keys));
            }

            ForeignKey[] var8 = (ForeignKey[])((ForeignKey[])importedKeyList.toArray(new ForeignKey[importedKeyList.size()]));
            return var8;
         } finally {
            if (keys != null) {
               try {
                  keys.close();
               } catch (Exception var15) {
               }
            }

         }
      }
   }

   protected ForeignKey newForeignKey(ResultSet fkMeta) throws SQLException {
      ForeignKey fk = new ForeignKey();
      fk.setSchemaName(fkMeta.getString("FKTABLE_SCHEM"));
      fk.setTableName(fkMeta.getString("FKTABLE_NAME"));
      fk.setColumnName(fkMeta.getString("FKCOLUMN_NAME"));
      fk.setName(fkMeta.getString("FK_NAME"));
      fk.setPrimaryKeySchemaName(fkMeta.getString("PKTABLE_SCHEM"));
      fk.setPrimaryKeyTableName(fkMeta.getString("PKTABLE_NAME"));
      fk.setPrimaryKeyColumnName(fkMeta.getString("PKCOLUMN_NAME"));
      fk.setKeySequence(fkMeta.getShort("KEY_SEQ"));
      fk.setDeferred(fkMeta.getShort("DEFERRABILITY") == 5);
      int del = fkMeta.getShort("DELETE_RULE");
      switch (del) {
         case 0:
            fk.setDeleteAction(3);
            break;
         case 1:
         case 3:
         default:
            fk.setDeleteAction(2);
            break;
         case 2:
            fk.setDeleteAction(4);
            break;
         case 4:
            fk.setDeleteAction(5);
      }

      return fk;
   }

   protected String getTableNameForMetadata(String tableName) {
      return this.convertSchemaCase(tableName);
   }

   protected String getSchemaNameForMetadata(String schemaName) {
      if (schemaName == null) {
         schemaName = this.conf.getSchema();
      }

      return this.convertSchemaCase(schemaName);
   }

   protected String getCatalogNameForMetadata(String catalogName) {
      return this.convertSchemaCase(catalogName);
   }

   protected String getColumnNameForMetadata(String columnName) {
      return this.convertSchemaCase(columnName);
   }

   protected String convertSchemaCase(String objectName) {
      if (objectName == null) {
         return null;
      } else {
         String scase = this.getSchemaCase();
         if ("lower".equals(scase)) {
            return objectName.toLowerCase();
         } else {
            return "preserve".equals(scase) ? objectName : objectName.toUpperCase();
         }
      }
   }

   public String getSchemaCase() {
      return this.schemaCase;
   }

   private void beforeMetadataOperation(Connection c) {
      if (this.requiresAutoCommitForMetaData) {
         try {
            c.rollback();
         } catch (SQLException var4) {
         }

         try {
            if (!c.getAutoCommit()) {
               c.setAutoCommit(true);
            }
         } catch (SQLException var3) {
         }
      }

   }

   public Object getGeneratedKey(Column col, Connection conn) throws SQLException {
      if (this.lastGeneratedKeyQuery == null) {
         throw new StoreException(_loc.get("no-auto-assign"));
      } else {
         String query = this.lastGeneratedKeyQuery;
         if (query.indexOf(123) != -1) {
            query = MessageFormat.format(query, col.getName(), this.getFullName(col.getTable(), false), this.getGeneratedKeySequenceName(col));
         }

         PreparedStatement stmnt = this.prepareStatement(conn, query);
         ResultSet rs = null;

         Object var6;
         try {
            rs = this.executeQuery(conn, stmnt, query);
            var6 = this.getKey(rs, col);
         } finally {
            if (rs != null) {
               try {
                  rs.close();
               } catch (SQLException var16) {
               }
            }

            if (stmnt != null) {
               try {
                  stmnt.close();
               } catch (SQLException var15) {
               }
            }

         }

         return var6;
      }
   }

   protected String getGeneratedKeySequenceName(Column col) {
      String tname = col.getTableName();
      String cname = col.getName();
      int max = this.maxAutoAssignNameLength;
      int extraChars = -max + tname.length() + 1 + cname.length() + 4;
      if (extraChars > 0) {
         tname = tname.substring(0, tname.length() - extraChars);
      }

      StringBuffer buf = new StringBuffer(max);
      buf.append(tname).append("_").append(cname).append("_SEQ");
      return buf.toString();
   }

   public void setConfiguration(Configuration conf) {
      this.conf = (JDBCConfiguration)conf;
      this.log = this.conf.getLog("openjpa.jdbc.JDBC");
      if (this.log.isWarnEnabled() && !this.isSupported()) {
         this.log.warn(_loc.get("dict-not-supported", (Object)this.getClass()));
      }

   }

   private boolean isSupported() {
      Class c;
      for(c = this.getClass(); !c.getName().startsWith("org.apache.openjpa."); c = c.getSuperclass()) {
      }

      return c != DBDictionary.class;
   }

   public void startConfiguration() {
   }

   public void endConfiguration() {
      InputStream in = DBDictionary.class.getResourceAsStream("sql-keywords.rsrc");

      try {
         String keywords = (new BufferedReader(new InputStreamReader(in))).readLine();
         in.close();
         this.reservedWordSet.addAll(Arrays.asList(Strings.split(keywords, ",", 0)));
      } catch (IOException var11) {
         throw new GeneralException(var11);
      } finally {
         try {
            in.close();
         } catch (IOException var10) {
         }

      }

      if (this.reservedWords != null) {
         this.reservedWordSet.addAll(Arrays.asList(Strings.split(this.reservedWords.toUpperCase(), ",", 0)));
      }

      if (this.systemSchemas != null) {
         this.systemSchemaSet.addAll(Arrays.asList(Strings.split(this.systemSchemas.toUpperCase(), ",", 0)));
      }

      if (this.systemTables != null) {
         this.systemTableSet.addAll(Arrays.asList(Strings.split(this.systemTables.toUpperCase(), ",", 0)));
      }

      if (this.fixedSizeTypeNames != null) {
         this.fixedSizeTypeNameSet.addAll(Arrays.asList(Strings.split(this.fixedSizeTypeNames.toUpperCase(), ",", 0)));
      }

      this.nextSequenceQuery = StringUtils.trimToNull(this.nextSequenceQuery);
      if (this.selectWords != null) {
         this.selectWordSet.addAll(Arrays.asList(Strings.split(this.selectWords.toUpperCase(), ",", 0)));
      }

      SQLErrorCodeReader codeReader = new SQLErrorCodeReader();
      String rsrc = "sql-error-state-codes.xml";
      InputStream stream = this.getClass().getResourceAsStream(rsrc);
      String dictionaryClassName = this.getClass().getName();
      if (stream == null) {
         stream = DBDictionary.class.getResourceAsStream(rsrc);
         dictionaryClassName = this.getClass().getSuperclass().getName();
      }

      codeReader.parse(stream, dictionaryClassName, this);
   }

   public void addErrorCode(Integer errorType, String errorCode) {
      if (errorCode != null && errorCode.trim().length() != 0) {
         Set codes = (Set)this.sqlStateCodes.get(errorType);
         if (codes == null) {
            Set codes = new HashSet();
            codes.add(errorCode.trim());
            this.sqlStateCodes.put(errorType, codes);
         } else {
            codes.add(errorCode.trim());
         }

      }
   }

   public Connection decorate(Connection conn) throws SQLException {
      if (!this.connected) {
         this.connectedConfiguration(conn);
      }

      if (!StringUtils.isEmpty(this.initializationSQL)) {
         PreparedStatement stmnt = null;

         try {
            stmnt = conn.prepareStatement(this.initializationSQL);
            stmnt.execute();
         } catch (Exception var12) {
            if (this.log.isTraceEnabled()) {
               this.log.trace(var12.toString(), var12);
            }
         } finally {
            if (stmnt != null) {
               try {
                  stmnt.close();
               } catch (SQLException var11) {
               }
            }

         }
      }

      return conn;
   }

   public void handleWarning(SQLWarning warning) throws SQLException {
   }

   public OpenJPAException newStoreException(String msg, SQLException[] causes, Object failed) {
      if (causes != null && causes.length > 0) {
         OpenJPAException ret = this.narrow(msg, causes[0]);
         ret.setFailedObject(failed).setNestedThrowables(causes);
         return ret;
      } else {
         return (new StoreException(msg)).setFailedObject(failed).setNestedThrowables(causes);
      }
   }

   OpenJPAException narrow(String msg, SQLException ex) {
      String errorState = ex.getSQLState();
      int errorType = 0;
      Iterator iter = this.sqlStateCodes.keySet().iterator();

      while(iter.hasNext()) {
         Integer type = (Integer)iter.next();
         Set erroStates = (Set)this.sqlStateCodes.get(type);
         if (erroStates != null && erroStates.contains(errorState)) {
            errorType = type;
            break;
         }
      }

      switch (errorType) {
         case 1:
            return new LockException(msg);
         case 2:
            return new ObjectNotFoundException(msg);
         case 3:
            return new OptimisticException(msg);
         case 4:
            return new ReferentialIntegrityException(msg);
         case 5:
            return new ObjectExistsException(msg);
         default:
            return new StoreException(msg);
      }
   }

   public void closeDataSource(DataSource dataSource) {
      DataSourceFactory.closeDataSource(dataSource);
   }

   public String getVersionColumn(Column column, String tableAlias) {
      return column.toString();
   }

   public void insertBlobForStreamingLoad(Row row, Column col, JDBCStore store, Object ob, Select sel) throws SQLException {
      if (ob != null) {
         row.setBinaryStream(col, new ByteArrayInputStream(new byte[0]), 0);
      } else {
         row.setNull(col);
      }

   }

   public void insertClobForStreamingLoad(Row row, Column col, Object ob) throws SQLException {
      if (ob != null) {
         row.setCharacterStream(col, new CharArrayReader(new char[0]), 0);
      } else {
         row.setNull(col);
      }

   }

   public void updateBlob(Select sel, JDBCStore store, InputStream is) throws SQLException {
      SQLBuffer sql = sel.toSelect(true, store.getFetchConfiguration());
      ResultSet res = null;
      Connection conn = store.getConnection();
      PreparedStatement stmnt = null;

      try {
         stmnt = sql.prepareStatement(conn, store.getFetchConfiguration(), 1005, 1008);
         res = stmnt.executeQuery();
         if (!res.next()) {
            throw new InternalException(_loc.get("stream-exception"));
         }

         Blob blob = res.getBlob(1);
         OutputStream os = blob.setBinaryStream(1L);
         this.copy(is, os);
         os.close();
         res.updateBlob(1, blob);
         res.updateRow();
      } catch (IOException var23) {
         throw new StoreException(var23);
      } finally {
         if (res != null) {
            try {
               res.close();
            } catch (SQLException var22) {
            }
         }

         if (stmnt != null) {
            try {
               stmnt.close();
            } catch (SQLException var21) {
            }
         }

         if (conn != null) {
            try {
               conn.close();
            } catch (SQLException var20) {
            }
         }

      }

   }

   public void updateClob(Select sel, JDBCStore store, Reader reader) throws SQLException {
      SQLBuffer sql = sel.toSelect(true, store.getFetchConfiguration());
      ResultSet res = null;
      Connection conn = store.getConnection();
      PreparedStatement stmnt = null;

      try {
         stmnt = sql.prepareStatement(conn, store.getFetchConfiguration(), 1005, 1008);
         res = stmnt.executeQuery();
         if (!res.next()) {
            throw new InternalException(_loc.get("stream-exception"));
         }

         Clob clob = res.getClob(1);
         Writer writer = clob.setCharacterStream(1L);
         this.copy(reader, writer);
         writer.close();
         res.updateClob(1, clob);
         res.updateRow();
      } catch (IOException var23) {
         throw new StoreException(var23);
      } finally {
         if (res != null) {
            try {
               res.close();
            } catch (SQLException var22) {
            }
         }

         if (stmnt != null) {
            try {
               stmnt.close();
            } catch (SQLException var21) {
            }
         }

         if (conn != null) {
            try {
               conn.close();
            } catch (SQLException var20) {
            }
         }

      }

   }

   protected long copy(InputStream in, OutputStream out) throws IOException {
      byte[] copyBuffer = new byte[this.blobBufferSize];
      long bytesCopied = 0L;

      int read;
      for(int read = true; (read = in.read(copyBuffer, 0, copyBuffer.length)) != -1; bytesCopied += (long)read) {
         out.write(copyBuffer, 0, read);
      }

      return bytesCopied;
   }

   protected long copy(Reader reader, Writer writer) throws IOException {
      char[] copyBuffer = new char[this.clobBufferSize];
      long bytesCopied = 0L;

      int read;
      for(int read = true; (read = reader.read(copyBuffer, 0, copyBuffer.length)) != -1; bytesCopied += (long)read) {
         writer.write(copyBuffer, 0, read);
      }

      return bytesCopied;
   }

   public String getCastFunction(Val val, String func) {
      return func;
   }

   public void createIndexIfNecessary(Schema schema, String table, Column pkColumn) {
   }

   public int getBatchLimit() {
      return this.batchLimit;
   }

   public void setBatchLimit(int limit) {
      this.batchLimit = limit;
   }

   public boolean validateBatchProcess(RowImpl row, Column[] autoAssign, OpenJPAStateManager sm, ClassMapping cmd) {
      boolean disableBatch = false;
      if (this.getBatchLimit() == 0) {
         return false;
      } else {
         if (autoAssign != null && sm != null) {
            FieldMetaData[] fmd = cmd.getPrimaryKeyFields();

            for(int i = 0; !disableBatch && i < fmd.length; ++i) {
               if (fmd[i].getValueStrategy() == 3) {
                  disableBatch = true;
               }
            }
         }

         if (!disableBatch) {
            disableBatch = this.validateDBSpecificBatchProcess(disableBatch, row, autoAssign, sm, cmd);
         }

         return disableBatch;
      }
   }

   public boolean validateDBSpecificBatchProcess(boolean disableBatch, RowImpl row, Column[] autoAssign, OpenJPAStateManager sm, ClassMapping cmd) {
      return disableBatch;
   }

   protected ResultSet executeQuery(Connection conn, PreparedStatement stmnt, String sql) throws SQLException {
      return stmnt.executeQuery();
   }

   protected PreparedStatement prepareStatement(Connection conn, String sql) throws SQLException {
      return conn.prepareStatement(sql);
   }

   protected Sequence[] getSequence(ResultSet rs) throws SQLException {
      List seqList = new ArrayList();

      while(rs != null && rs.next()) {
         seqList.add(this.newSequence(rs));
      }

      return (Sequence[])((Sequence[])seqList.toArray(new Sequence[seqList.size()]));
   }

   protected Object getKey(ResultSet rs, Column col) throws SQLException {
      if (!rs.next()) {
         throw new StoreException(_loc.get("no-genkey"));
      } else {
         Object key = rs.getObject(1);
         if (key == null) {
            this.log.warn(_loc.get("invalid-genkey", (Object)col));
         }

         return key;
      }
   }

   protected void calculateValue(Val val, Select sel, ExpContext ctx, ExpState state, Path path, ExpState pathState) {
      val.calculateValue(sel, ctx, state, (Val)path, pathState);
   }

   public boolean isSelect(String sql) {
      Iterator i = this.selectWordSet.iterator();

      String cur;
      do {
         if (!i.hasNext()) {
            return false;
         }

         cur = (String)i.next();
      } while(sql.length() < cur.length() || !sql.substring(0, cur.length()).equalsIgnoreCase(cur));

      return true;
   }

   public void deleteStream(JDBCStore store, Select sel) throws SQLException {
   }

   public static class SerializedData {
      public final byte[] bytes;

      public SerializedData(byte[] bytes) {
         this.bytes = bytes;
      }
   }
}
