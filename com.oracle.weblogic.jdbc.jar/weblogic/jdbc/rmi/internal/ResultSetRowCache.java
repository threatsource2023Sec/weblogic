package weblogic.jdbc.rmi.internal;

import java.io.PrintStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;

public class ResultSetRowCache implements Serializable {
   static final long serialVersionUID = 819366818399178928L;
   private boolean isTrueSetFinished = false;
   private int cacheRows;
   private final int cacheCols;
   private Object[] recordCache;
   private transient int currRowIdx = 0;
   private transient boolean wasNullFlag = false;
   private transient boolean haveCalledGet = false;
   private transient ResultSetMetaDataCache mdCache = null;
   private static final String CONVERSIONERR = "this type conversion is unsupported when row caching is on";

   public ResultSetRowCache() {
      this.cacheCols = 0;
   }

   public ResultSetRowCache(int rows_to_cache, java.sql.ResultSet rs, ResultSetMetaDataCache md) throws SQLException {
      this.cacheRows = 0;
      this.cacheCols = md.getColumnCount();
      this.recordCache = new Object[rows_to_cache * this.cacheCols];

      for(int row = 0; row < rows_to_cache; ++row) {
         if (!rs.next()) {
            this.isTrueSetFinished = true;
            break;
         }

         int offset = row * this.cacheCols;

         for(int col = 0; col < this.cacheCols; ++col) {
            this.recordCache[offset + col] = getFieldAsObject(md.getColumnTypeZeroBased(col), col + 1, rs);
         }

         ++this.cacheRows;
      }

   }

   static boolean isCacheable(ResultSetMetaDataCache md) {
      int colCnt = md.getColumnCount();

      for(int col = 0; col < colCnt; ++col) {
         int sqlType = md.getColumnTypeZeroBased(col);
         if (!isCacheable(sqlType)) {
            return false;
         }
      }

      return true;
   }

   void setMetaDataCache(ResultSetMetaDataCache md) {
      this.mdCache = md;
   }

   synchronized void beforeFirstRow() {
      this.currRowIdx = -1;
   }

   synchronized int getRowCount() {
      return this.cacheRows;
   }

   synchronized boolean wasNull() throws SQLException {
      if (!this.haveCalledGet) {
         throw new SQLException("No getXXX() has been called on a column of the current row");
      } else {
         return this.wasNullFlag;
      }
   }

   String getNString(int columnIndex) throws SQLException {
      return this.getString(columnIndex);
   }

   synchronized String getString(int column_index) throws SQLException {
      Object o = this.getObject(column_index);
      return this.checkNull(o) ? null : o.toString();
   }

   synchronized boolean getBoolean(int column_index) throws SQLException {
      Object o = this.getObject(column_index);
      if (this.checkNull(o)) {
         return false;
      } else {
         String boolstr = o.toString();
         if (boolstr == null) {
            return false;
         } else if (boolstr.length() == 0) {
            return false;
         } else {
            boolstr = boolstr.trim();
            if (boolstr.equalsIgnoreCase("true")) {
               return true;
            } else if (boolstr.equalsIgnoreCase("yes")) {
               return true;
            } else if (boolstr.equalsIgnoreCase("no")) {
               return false;
            } else if (boolstr.equalsIgnoreCase("false")) {
               return false;
            } else {
               try {
                  if (boolstr.indexOf(".") != -1) {
                     double d = Double.parseDouble(boolstr);
                     return d != 0.0;
                  } else {
                     long l = Long.parseLong(boolstr);
                     return l != 0L;
                  }
               } catch (Exception var6) {
                  return false;
               }
            }
         }
      }
   }

   synchronized byte getByte(int column_index) throws SQLException {
      Object val = this.getObject(column_index);
      if (this.checkNull(val)) {
         return 0;
      } else {
         switch (this.mdCache.getColumnType(column_index)) {
            case -7:
               if ((Boolean)val) {
                  return 1;
               }

               return 0;
            case -6:
            case 4:
            case 5:
               return ((Integer)val).byteValue();
            case -5:
               return ((Long)val).byteValue();
            case -4:
            case -3:
            case -2:
            case -1:
            case 0:
            case 9:
            case 10:
            case 11:
            default:
               throw new SQLException("this type conversion is unsupported when row caching is on");
            case 1:
            case 12:
               return Byte.parseByte((String)val);
            case 2:
            case 3:
            case 6:
            case 7:
            case 8:
               return ((BigDecimal)val).byteValue();
         }
      }
   }

   synchronized short getShort(int column_index) throws SQLException {
      Object val = this.getObject(column_index);
      if (this.checkNull(val)) {
         return 0;
      } else {
         switch (this.mdCache.getColumnType(column_index)) {
            case -7:
               if ((Boolean)val) {
                  return 1;
               }

               return 0;
            case -6:
            case 4:
            case 5:
               return ((Integer)val).shortValue();
            case -5:
               return ((Long)val).shortValue();
            case -4:
            case -3:
            case -2:
            case -1:
            case 0:
            case 9:
            case 10:
            case 11:
            default:
               throw new SQLException("this type conversion is unsupported when row caching is on");
            case 1:
            case 12:
               return Short.parseShort((String)val);
            case 2:
            case 3:
            case 6:
            case 7:
            case 8:
               return ((BigDecimal)val).shortValue();
         }
      }
   }

   synchronized int getInt(int column_index) throws SQLException {
      Object val = this.getObject(column_index);
      if (this.checkNull(val)) {
         return 0;
      } else {
         switch (this.mdCache.getColumnType(column_index)) {
            case -7:
               if ((Boolean)val) {
                  return 1;
               }

               return 0;
            case -6:
            case 4:
            case 5:
               return (Integer)val;
            case -5:
               return ((Long)val).intValue();
            case -4:
            case -3:
            case -2:
            case -1:
            case 0:
            case 9:
            case 10:
            case 11:
            default:
               throw new SQLException("this type conversion is unsupported when row caching is on");
            case 1:
            case 12:
               return Integer.parseInt((String)val);
            case 2:
            case 3:
            case 6:
            case 7:
            case 8:
               return ((BigDecimal)val).intValue();
         }
      }
   }

   synchronized long getLong(int column_index) throws SQLException {
      Object val = this.getObject(column_index);
      if (this.checkNull(val)) {
         return 0L;
      } else {
         switch (this.mdCache.getColumnType(column_index)) {
            case -7:
               if ((Boolean)val) {
                  return 1L;
               }

               return 0L;
            case -6:
            case 4:
            case 5:
               return ((Integer)val).longValue();
            case -5:
               return (Long)val;
            case -4:
            case -3:
            case -2:
            case -1:
            case 0:
            case 9:
            case 10:
            case 11:
            default:
               throw new SQLException("this type conversion is unsupported when row caching is on");
            case 1:
            case 12:
               return Long.parseLong((String)val);
            case 2:
            case 3:
            case 6:
            case 7:
            case 8:
               return ((BigDecimal)val).longValue();
         }
      }
   }

   synchronized float getFloat(int column_index) throws SQLException {
      Object val = this.getObject(column_index);
      if (this.checkNull(val)) {
         return 0.0F;
      } else {
         switch (this.mdCache.getColumnType(column_index)) {
            case -7:
               if ((Boolean)val) {
                  return 1.0F;
               }

               return 0.0F;
            case -6:
            case 4:
            case 5:
               return ((Integer)val).floatValue();
            case -5:
               return ((Long)val).floatValue();
            case -4:
            case -3:
            case -2:
            case -1:
            case 0:
            case 9:
            case 10:
            case 11:
            default:
               throw new SQLException("this type conversion is unsupported when row caching is on");
            case 1:
            case 12:
               return Float.parseFloat((String)val);
            case 2:
            case 3:
            case 6:
            case 7:
            case 8:
               return ((BigDecimal)val).floatValue();
         }
      }
   }

   synchronized double getDouble(int column_index) throws SQLException {
      Object val = this.getObject(column_index);
      if (this.checkNull(val)) {
         return 0.0;
      } else {
         switch (this.mdCache.getColumnType(column_index)) {
            case -7:
               if ((Boolean)val) {
                  return 1.0;
               }

               return 0.0;
            case -6:
            case 4:
            case 5:
               return ((Integer)val).doubleValue();
            case -5:
               return ((Long)val).doubleValue();
            case -4:
            case -3:
            case -2:
            case -1:
            case 0:
            case 9:
            case 10:
            case 11:
            default:
               throw new SQLException("this type conversion is unsupported when row caching is on");
            case 1:
            case 12:
               return Double.parseDouble((String)val);
            case 2:
            case 3:
            case 6:
            case 7:
            case 8:
               return ((BigDecimal)val).doubleValue();
         }
      }
   }

   synchronized BigDecimal getBigDecimal(int column_index) throws SQLException {
      Object val = this.getObject(column_index);
      if (this.checkNull(val)) {
         return null;
      } else {
         switch (this.mdCache.getColumnType(column_index)) {
            case -7:
               if ((Boolean)val) {
                  return new BigDecimal(1.0);
               }

               return new BigDecimal(0.0);
            case -6:
            case 4:
            case 5:
               return new BigDecimal(((Integer)val).doubleValue());
            case -5:
               return new BigDecimal(((Long)val).doubleValue());
            case -4:
            case -3:
            case -2:
            case -1:
            case 0:
            case 9:
            case 10:
            case 11:
            default:
               throw new SQLException("this type conversion is unsupported when row caching is on");
            case 1:
            case 12:
               return new BigDecimal((String)val);
            case 2:
            case 3:
            case 6:
            case 7:
            case 8:
               return (BigDecimal)val;
         }
      }
   }

   synchronized BigDecimal getBigDecimal(int column_index, int scale) throws SQLException {
      BigDecimal full_scaled = this.getBigDecimal(column_index);
      return full_scaled.setScale(scale);
   }

   synchronized byte[] getBytes(int column_index) throws SQLException {
      Object val = this.getObject(column_index);
      if (this.checkNull(val)) {
         return null;
      } else {
         switch (this.mdCache.getColumnType(column_index)) {
            case -3:
            case -2:
               return (byte[])((byte[])val);
            default:
               throw new SQLException("this type conversion is unsupported when row caching is on");
         }
      }
   }

   synchronized Date getDate(int column_index) throws SQLException {
      Object val = this.getObject(column_index);
      if (this.checkNull(val)) {
         return null;
      } else {
         switch (this.mdCache.getColumnType(column_index)) {
            case 1:
            case 12:
               return Date.valueOf((String)val);
            case 91:
               return (Date)val;
            case 93:
               return new Date(((Timestamp)val).getTime());
            default:
               throw new SQLException("this type conversion is unsupported when row caching is on");
         }
      }
   }

   synchronized Time getTime(int column_index) throws SQLException {
      Object val = this.getObject(column_index);
      if (this.checkNull(val)) {
         return null;
      } else {
         switch (this.mdCache.getColumnType(column_index)) {
            case 1:
            case 12:
               return Time.valueOf((String)val);
            case 92:
               return (Time)val;
            case 93:
               return new Time(((Timestamp)val).getTime());
            default:
               throw new SQLException("this type conversion is unsupported when row caching is on");
         }
      }
   }

   synchronized Timestamp getTimestamp(int column_index) throws SQLException {
      Object val = this.getObject(column_index);
      if (this.checkNull(val)) {
         return null;
      } else {
         switch (this.mdCache.getColumnType(column_index)) {
            case 1:
            case 12:
               return Timestamp.valueOf((String)val);
            case 91:
               if (val instanceof Timestamp) {
                  return (Timestamp)val;
               }

               return new Timestamp(((Date)val).getTime());
            case 92:
               return new Timestamp(((Time)val).getTime());
            case 93:
               return (Timestamp)val;
            default:
               throw new SQLException("this type conversion is unsupported when row caching is on");
         }
      }
   }

   synchronized Object getObject(int column_index) throws SQLException {
      if (column_index >= 1 && column_index <= this.cacheCols) {
         this.haveCalledGet = true;
         int idx = this.currRowIdx * this.cacheCols + (column_index - 1);
         return this.recordCache[idx];
      } else {
         String msg = "Invalid column index: " + column_index;
         throw new SQLException(msg);
      }
   }

   synchronized boolean isTrueSetFinished() {
      return this.isTrueSetFinished;
   }

   synchronized void setTrueSetFinished(boolean is_done) {
      this.isTrueSetFinished = is_done;
   }

   synchronized boolean next() {
      this.haveCalledGet = false;
      ++this.currRowIdx;
      return this.currRowIdx < this.cacheRows;
   }

   public synchronized void dumpCache(PrintStream out) {
      for(int row = 0; row < this.cacheRows; ++row) {
         for(int col = 0; col < this.cacheCols; ++col) {
            out.print("ROW: " + row + "\tCOL: " + col + "\tOBJ: ");
            if (this.recordCache[row * this.cacheCols + col] != null) {
               System.out.println(this.recordCache[row * this.cacheCols + col]);
            } else {
               System.out.println("NULL");
            }
         }
      }

   }

   private static Object getFieldAsObject(int sqlType, int column, java.sql.ResultSet rs) throws SQLException {
      Object retval;
      switch (sqlType) {
         case -16:
         case -8:
         case -4:
         case -1:
         case 0:
         case 70:
         case 1111:
         case 2000:
         case 2001:
         case 2002:
         case 2003:
         case 2004:
         case 2005:
         case 2006:
         case 2009:
         case 2011:
         default:
            retval = null;
            break;
         case -15:
         case -9:
            retval = rs.getNString(column);
            break;
         case -7:
         case 16:
            retval = new Boolean(rs.getBoolean(column));
            break;
         case -6:
         case 4:
         case 5:
            retval = new Integer(rs.getInt(column));
            break;
         case -5:
            retval = new Long(rs.getLong(column));
            break;
         case -3:
         case -2:
            retval = rs.getBytes(column);
            break;
         case 1:
         case 12:
            retval = rs.getString(column);
            break;
         case 2:
         case 3:
         case 6:
         case 7:
         case 8:
            try {
               retval = rs.getBigDecimal(column);
            } catch (SQLException var5) {
               retval = new BigDecimal(rs.getString(column));
            }
            break;
         case 91:
            retval = rs.getDate(column);
            Timestamp ts = rs.getTimestamp(column);
            if (retval != null && ts != null && ts.getTime() != ((Date)retval).getTime()) {
               retval = ts;
            }
            break;
         case 92:
            retval = rs.getTime(column);
            break;
         case 93:
            retval = rs.getTimestamp(column);
      }

      if (rs.wasNull()) {
         retval = null;
      }

      return retval;
   }

   private static boolean isCacheable(int sqlType) {
      switch (sqlType) {
         case -15:
         case -9:
         case -7:
         case -6:
         case -5:
         case -3:
         case -2:
         case 1:
         case 2:
         case 3:
         case 4:
         case 5:
         case 6:
         case 7:
         case 8:
         case 12:
         case 16:
         case 91:
         case 92:
         case 93:
            return true;
         default:
            return false;
      }
   }

   private boolean checkNull(Object o) {
      this.wasNullFlag = o == null;
      return this.wasNullFlag;
   }
}
