package org.apache.openjpa.slice.jdbc;

import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Array;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Date;
import java.sql.Ref;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.Statement;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;

class DistributedResultSet implements ResultSet {
   LinkedList comps = new LinkedList();
   ResultSet current;
   int cursor = -1;

   public void add(ResultSet rs) {
      try {
         if (rs.first()) {
            this.comps.add(rs);
         }
      } catch (SQLException var3) {
      }

   }

   public boolean absolute(int arg0) throws SQLException {
      throw new UnsupportedOperationException();
   }

   public void afterLast() throws SQLException {
      this.current = null;
      this.cursor = this.comps.size();
   }

   public void beforeFirst() throws SQLException {
      this.current = null;
      this.cursor = -1;
   }

   public void cancelRowUpdates() throws SQLException {
      throw new UnsupportedOperationException();
   }

   public void clearWarnings() throws SQLException {
      Iterator i$ = this.comps.iterator();

      while(i$.hasNext()) {
         ResultSet rs = (ResultSet)i$.next();
         rs.clearWarnings();
      }

   }

   public void close() throws SQLException {
      Iterator i$ = this.comps.iterator();

      while(i$.hasNext()) {
         ResultSet rs = (ResultSet)i$.next();
         rs.close();
      }

   }

   public void deleteRow() throws SQLException {
      this.current.deleteRow();
   }

   public int findColumn(String arg0) throws SQLException {
      return 0;
   }

   public boolean first() throws SQLException {
      if (this.comps.isEmpty()) {
         return false;
      } else {
         this.cursor = 0;
         this.current = (ResultSet)this.comps.get(0);
         return true;
      }
   }

   public Array getArray(int arg0) throws SQLException {
      return this.current.getArray(arg0);
   }

   public Array getArray(String arg0) throws SQLException {
      return this.current.getArray(arg0);
   }

   public InputStream getAsciiStream(int arg0) throws SQLException {
      return this.current.getAsciiStream(arg0);
   }

   public InputStream getAsciiStream(String arg0) throws SQLException {
      return this.current.getAsciiStream(arg0);
   }

   public BigDecimal getBigDecimal(int arg0) throws SQLException {
      return this.current.getBigDecimal(arg0);
   }

   public BigDecimal getBigDecimal(String arg0) throws SQLException {
      return this.current.getBigDecimal(arg0);
   }

   public BigDecimal getBigDecimal(int arg0, int arg1) throws SQLException {
      return this.current.getBigDecimal(arg0, arg1);
   }

   public BigDecimal getBigDecimal(String arg0, int arg1) throws SQLException {
      return this.current.getBigDecimal(arg0, arg1);
   }

   public InputStream getBinaryStream(int arg0) throws SQLException {
      return this.current.getBinaryStream(arg0);
   }

   public InputStream getBinaryStream(String arg0) throws SQLException {
      return this.current.getBinaryStream(arg0);
   }

   public Blob getBlob(int arg0) throws SQLException {
      return this.current.getBlob(arg0);
   }

   public Blob getBlob(String arg0) throws SQLException {
      return null;
   }

   public boolean getBoolean(int arg0) throws SQLException {
      return false;
   }

   public boolean getBoolean(String arg0) throws SQLException {
      return false;
   }

   public byte getByte(int arg0) throws SQLException {
      return 0;
   }

   public byte getByte(String arg0) throws SQLException {
      return 0;
   }

   public byte[] getBytes(int arg0) throws SQLException {
      return null;
   }

   public byte[] getBytes(String arg0) throws SQLException {
      return null;
   }

   public Reader getCharacterStream(int arg0) throws SQLException {
      return null;
   }

   public Reader getCharacterStream(String arg0) throws SQLException {
      return null;
   }

   public Clob getClob(int arg0) throws SQLException {
      return null;
   }

   public Clob getClob(String arg0) throws SQLException {
      return null;
   }

   public int getConcurrency() throws SQLException {
      return 0;
   }

   public String getCursorName() throws SQLException {
      return null;
   }

   public Date getDate(int arg0) throws SQLException {
      return null;
   }

   public Date getDate(String arg0) throws SQLException {
      return null;
   }

   public Date getDate(int arg0, Calendar arg1) throws SQLException {
      return null;
   }

   public Date getDate(String arg0, Calendar arg1) throws SQLException {
      return null;
   }

   public double getDouble(int arg0) throws SQLException {
      return 0.0;
   }

   public double getDouble(String arg0) throws SQLException {
      return 0.0;
   }

   public int getFetchDirection() throws SQLException {
      return 0;
   }

   public int getFetchSize() throws SQLException {
      return 0;
   }

   public float getFloat(int arg0) throws SQLException {
      return 0.0F;
   }

   public float getFloat(String arg0) throws SQLException {
      return 0.0F;
   }

   public int getInt(int arg0) throws SQLException {
      return 0;
   }

   public int getInt(String arg0) throws SQLException {
      return 0;
   }

   public long getLong(int arg0) throws SQLException {
      return 0L;
   }

   public long getLong(String arg0) throws SQLException {
      return 0L;
   }

   public ResultSetMetaData getMetaData() throws SQLException {
      return null;
   }

   public Object getObject(int arg0) throws SQLException {
      return null;
   }

   public Object getObject(String arg0) throws SQLException {
      return null;
   }

   public Object getObject(int arg0, Map arg1) throws SQLException {
      return null;
   }

   public Object getObject(String arg0, Map arg1) throws SQLException {
      return null;
   }

   public Ref getRef(int arg0) throws SQLException {
      return null;
   }

   public Ref getRef(String arg0) throws SQLException {
      return null;
   }

   public int getRow() throws SQLException {
      return 0;
   }

   public short getShort(int arg0) throws SQLException {
      return 0;
   }

   public short getShort(String arg0) throws SQLException {
      return 0;
   }

   public Statement getStatement() throws SQLException {
      return null;
   }

   public String getString(int arg0) throws SQLException {
      return null;
   }

   public String getString(String arg0) throws SQLException {
      return null;
   }

   public Time getTime(int arg0) throws SQLException {
      return null;
   }

   public Time getTime(String arg0) throws SQLException {
      return null;
   }

   public Time getTime(int arg0, Calendar arg1) throws SQLException {
      return null;
   }

   public Time getTime(String arg0, Calendar arg1) throws SQLException {
      return null;
   }

   public Timestamp getTimestamp(int arg0) throws SQLException {
      return null;
   }

   public Timestamp getTimestamp(String arg0) throws SQLException {
      return null;
   }

   public Timestamp getTimestamp(int arg0, Calendar arg1) throws SQLException {
      return null;
   }

   public Timestamp getTimestamp(String arg0, Calendar arg1) throws SQLException {
      return null;
   }

   public int getType() throws SQLException {
      return 0;
   }

   public URL getURL(int arg0) throws SQLException {
      return null;
   }

   public URL getURL(String arg0) throws SQLException {
      return null;
   }

   public InputStream getUnicodeStream(int arg0) throws SQLException {
      return null;
   }

   public InputStream getUnicodeStream(String arg0) throws SQLException {
      return null;
   }

   public SQLWarning getWarnings() throws SQLException {
      return null;
   }

   public void insertRow() throws SQLException {
   }

   public boolean isAfterLast() throws SQLException {
      return this.current == null && this.cursor >= this.comps.size();
   }

   public boolean isBeforeFirst() throws SQLException {
      return this.current == null && this.cursor < 0;
   }

   public boolean isFirst() throws SQLException {
      return this.current != null && this.current.isFirst() && this.cursor == 0;
   }

   public boolean isLast() throws SQLException {
      return this.current != null && this.current.isLast() && this.cursor == this.comps.size() - 1;
   }

   public boolean last() throws SQLException {
      if (this.comps.isEmpty()) {
         return false;
      } else {
         this.cursor = this.comps.size() - 1;
         return false;
      }
   }

   public void moveToCurrentRow() throws SQLException {
   }

   public void moveToInsertRow() throws SQLException {
   }

   public boolean next() throws SQLException {
      if (this.current == null) {
         this.current = (ResultSet)this.comps.get(0);
         this.cursor = 0;
      }

      if (this.current.next()) {
         return true;
      } else {
         ++this.cursor;
         if (this.cursor < this.comps.size()) {
            this.current = (ResultSet)this.comps.get(this.cursor);
         }

         return this.cursor < this.comps.size();
      }
   }

   public boolean previous() throws SQLException {
      return this.current.previous();
   }

   public void refreshRow() throws SQLException {
   }

   public boolean relative(int arg0) throws SQLException {
      return false;
   }

   public boolean rowDeleted() throws SQLException {
      return false;
   }

   public boolean rowInserted() throws SQLException {
      return false;
   }

   public boolean rowUpdated() throws SQLException {
      return false;
   }

   public void setFetchDirection(int arg0) throws SQLException {
   }

   public void setFetchSize(int arg0) throws SQLException {
   }

   public void updateArray(int arg0, Array arg1) throws SQLException {
   }

   public void updateArray(String arg0, Array arg1) throws SQLException {
   }

   public void updateAsciiStream(int arg0, InputStream arg1, int arg2) throws SQLException {
   }

   public void updateAsciiStream(String arg0, InputStream arg1, int arg2) throws SQLException {
   }

   public void updateBigDecimal(int arg0, BigDecimal arg1) throws SQLException {
   }

   public void updateBigDecimal(String arg0, BigDecimal arg1) throws SQLException {
   }

   public void updateBinaryStream(int arg0, InputStream arg1, int arg2) throws SQLException {
   }

   public void updateBinaryStream(String arg0, InputStream arg1, int arg2) throws SQLException {
   }

   public void updateBlob(int arg0, Blob arg1) throws SQLException {
   }

   public void updateBlob(String arg0, Blob arg1) throws SQLException {
   }

   public void updateBoolean(int arg0, boolean arg1) throws SQLException {
   }

   public void updateBoolean(String arg0, boolean arg1) throws SQLException {
   }

   public void updateByte(int arg0, byte arg1) throws SQLException {
   }

   public void updateByte(String arg0, byte arg1) throws SQLException {
   }

   public void updateBytes(int arg0, byte[] arg1) throws SQLException {
   }

   public void updateBytes(String arg0, byte[] arg1) throws SQLException {
   }

   public void updateCharacterStream(int arg0, Reader arg1, int arg2) throws SQLException {
   }

   public void updateCharacterStream(String arg0, Reader arg1, int arg2) throws SQLException {
   }

   public void updateClob(int arg0, Clob arg1) throws SQLException {
   }

   public void updateClob(String arg0, Clob arg1) throws SQLException {
   }

   public void updateDate(int arg0, Date arg1) throws SQLException {
   }

   public void updateDate(String arg0, Date arg1) throws SQLException {
   }

   public void updateDouble(int arg0, double arg1) throws SQLException {
   }

   public void updateDouble(String arg0, double arg1) throws SQLException {
   }

   public void updateFloat(int arg0, float arg1) throws SQLException {
   }

   public void updateFloat(String arg0, float arg1) throws SQLException {
   }

   public void updateInt(int arg0, int arg1) throws SQLException {
   }

   public void updateInt(String arg0, int arg1) throws SQLException {
   }

   public void updateLong(int arg0, long arg1) throws SQLException {
   }

   public void updateLong(String arg0, long arg1) throws SQLException {
   }

   public void updateNull(int arg0) throws SQLException {
   }

   public void updateNull(String arg0) throws SQLException {
   }

   public void updateObject(int arg0, Object arg1) throws SQLException {
   }

   public void updateObject(String arg0, Object arg1) throws SQLException {
   }

   public void updateObject(int arg0, Object arg1, int arg2) throws SQLException {
   }

   public void updateObject(String arg0, Object arg1, int arg2) throws SQLException {
   }

   public void updateRef(int arg0, Ref arg1) throws SQLException {
   }

   public void updateRef(String arg0, Ref arg1) throws SQLException {
   }

   public void updateRow() throws SQLException {
   }

   public void updateShort(int arg0, short arg1) throws SQLException {
   }

   public void updateShort(String arg0, short arg1) throws SQLException {
   }

   public void updateString(int arg0, String arg1) throws SQLException {
   }

   public void updateString(String arg0, String arg1) throws SQLException {
   }

   public void updateTime(int arg0, Time arg1) throws SQLException {
   }

   public void updateTime(String arg0, Time arg1) throws SQLException {
   }

   public void updateTimestamp(int arg0, Timestamp arg1) throws SQLException {
   }

   public void updateTimestamp(String arg0, Timestamp arg1) throws SQLException {
   }

   public boolean wasNull() throws SQLException {
      return false;
   }
}
