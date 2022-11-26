package org.apache.openjpa.slice.jdbc;

import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Array;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Date;
import java.sql.ParameterMetaData;
import java.sql.PreparedStatement;
import java.sql.Ref;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Iterator;

class DistributedPreparedStatement extends DistributedTemplate implements PreparedStatement {
   DistributedPreparedStatement(DistributedConnection c) {
      super(c);
   }

   public void clearParameters() throws SQLException {
      Iterator i$ = this.iterator();

      while(i$.hasNext()) {
         PreparedStatement s = (PreparedStatement)i$.next();
         s.clearParameters();
      }

   }

   public boolean execute() throws SQLException {
      boolean ret = true;

      PreparedStatement s;
      for(Iterator i$ = this.iterator(); i$.hasNext(); ret &= s.execute()) {
         s = (PreparedStatement)i$.next();
      }

      return ret;
   }

   public ResultSet executeQuery() throws SQLException {
      DistributedResultSet mrs = new DistributedResultSet();
      Iterator i$ = this.iterator();

      while(i$.hasNext()) {
         PreparedStatement t = (PreparedStatement)i$.next();
         mrs.add(t.executeQuery());
      }

      return mrs;
   }

   public int executeUpdate() throws SQLException {
      int ret = 0;

      PreparedStatement t;
      for(Iterator i$ = this.iterator(); i$.hasNext(); ret += t.executeUpdate()) {
         t = (PreparedStatement)i$.next();
      }

      return ret;
   }

   public ResultSetMetaData getMetaData() throws SQLException {
      return ((PreparedStatement)this.master).getMetaData();
   }

   public ParameterMetaData getParameterMetaData() throws SQLException {
      throw new UnsupportedOperationException();
   }

   public void setArray(int i, Array x) throws SQLException {
      Iterator i$ = this.iterator();

      while(i$.hasNext()) {
         PreparedStatement t = (PreparedStatement)i$.next();
         t.setArray(i, x);
      }

   }

   public void setAsciiStream(int arg0, InputStream arg1, int arg2) throws SQLException {
      Iterator i$ = this.iterator();

      while(i$.hasNext()) {
         PreparedStatement t = (PreparedStatement)i$.next();
         t.setAsciiStream(arg0, arg1, arg2);
      }

   }

   public void setBigDecimal(int arg0, BigDecimal arg1) throws SQLException {
      Iterator i$ = this.iterator();

      while(i$.hasNext()) {
         PreparedStatement t = (PreparedStatement)i$.next();
         t.setBigDecimal(arg0, arg1);
      }

   }

   public void setBinaryStream(int arg0, InputStream arg1, int arg2) throws SQLException {
      Iterator i$ = this.iterator();

      while(i$.hasNext()) {
         PreparedStatement t = (PreparedStatement)i$.next();
         t.setBinaryStream(arg0, arg1, arg2);
      }

   }

   public void setBlob(int arg0, Blob arg1) throws SQLException {
      Iterator i$ = this.iterator();

      while(i$.hasNext()) {
         PreparedStatement t = (PreparedStatement)i$.next();
         t.setBlob(arg0, arg1);
      }

   }

   public void setBoolean(int arg0, boolean arg1) throws SQLException {
      Iterator i$ = this.iterator();

      while(i$.hasNext()) {
         PreparedStatement t = (PreparedStatement)i$.next();
         t.setBoolean(arg0, arg1);
      }

   }

   public void setByte(int arg0, byte arg1) throws SQLException {
      Iterator i$ = this.iterator();

      while(i$.hasNext()) {
         PreparedStatement t = (PreparedStatement)i$.next();
         t.setByte(arg0, arg1);
      }

   }

   public void setBytes(int arg0, byte[] arg1) throws SQLException {
      Iterator i$ = this.iterator();

      while(i$.hasNext()) {
         PreparedStatement t = (PreparedStatement)i$.next();
         t.setBytes(arg0, arg1);
      }

   }

   public void setCharacterStream(int arg0, Reader arg1, int arg2) throws SQLException {
      Iterator i$ = this.iterator();

      while(i$.hasNext()) {
         PreparedStatement t = (PreparedStatement)i$.next();
         t.setCharacterStream(arg0, arg1, arg2);
      }

   }

   public void setClob(int arg0, Clob arg1) throws SQLException {
      Iterator i$ = this.iterator();

      while(i$.hasNext()) {
         PreparedStatement t = (PreparedStatement)i$.next();
         t.setClob(arg0, arg1);
      }

   }

   public void setDate(int arg0, Date arg1) throws SQLException {
      Iterator i$ = this.iterator();

      while(i$.hasNext()) {
         PreparedStatement t = (PreparedStatement)i$.next();
         t.setDate(arg0, arg1);
      }

   }

   public void setDate(int arg0, Date arg1, Calendar arg2) throws SQLException {
      Iterator i$ = this.iterator();

      while(i$.hasNext()) {
         PreparedStatement t = (PreparedStatement)i$.next();
         t.setDate(arg0, arg1, arg2);
      }

   }

   public void setDouble(int arg0, double arg1) throws SQLException {
      Iterator i$ = this.iterator();

      while(i$.hasNext()) {
         PreparedStatement t = (PreparedStatement)i$.next();
         t.setDouble(arg0, arg1);
      }

   }

   public void setFloat(int arg0, float arg1) throws SQLException {
      Iterator i$ = this.iterator();

      while(i$.hasNext()) {
         PreparedStatement t = (PreparedStatement)i$.next();
         t.setFloat(arg0, arg1);
      }

   }

   public void setInt(int arg0, int arg1) throws SQLException {
      Iterator i$ = this.iterator();

      while(i$.hasNext()) {
         PreparedStatement t = (PreparedStatement)i$.next();
         t.setInt(arg0, arg1);
      }

   }

   public void setLong(int arg0, long arg1) throws SQLException {
      Iterator i$ = this.iterator();

      while(i$.hasNext()) {
         PreparedStatement t = (PreparedStatement)i$.next();
         t.setLong(arg0, arg1);
      }

   }

   public void setNull(int arg0, int arg1) throws SQLException {
      Iterator i$ = this.iterator();

      while(i$.hasNext()) {
         PreparedStatement t = (PreparedStatement)i$.next();
         t.setNull(arg0, arg1);
      }

   }

   public void setNull(int arg0, int arg1, String arg2) throws SQLException {
      Iterator i$ = this.iterator();

      while(i$.hasNext()) {
         PreparedStatement t = (PreparedStatement)i$.next();
         t.setNull(arg0, arg1, arg2);
      }

   }

   public void setObject(int arg0, Object arg1) throws SQLException {
      Iterator i$ = this.iterator();

      while(i$.hasNext()) {
         PreparedStatement t = (PreparedStatement)i$.next();
         t.setObject(arg0, arg1);
      }

   }

   public void setObject(int arg0, Object arg1, int arg2) throws SQLException {
      Iterator i$ = this.iterator();

      while(i$.hasNext()) {
         PreparedStatement t = (PreparedStatement)i$.next();
         t.setObject(arg0, arg1, arg2);
      }

   }

   public void setObject(int arg0, Object arg1, int arg2, int arg3) throws SQLException {
      Iterator i$ = this.iterator();

      while(i$.hasNext()) {
         PreparedStatement t = (PreparedStatement)i$.next();
         t.setObject(arg0, arg1, arg2, arg3);
      }

   }

   public void setRef(int arg0, Ref arg1) throws SQLException {
      Iterator i$ = this.iterator();

      while(i$.hasNext()) {
         PreparedStatement t = (PreparedStatement)i$.next();
         t.setRef(arg0, arg1);
      }

   }

   public void setShort(int arg0, short arg1) throws SQLException {
      Iterator i$ = this.iterator();

      while(i$.hasNext()) {
         PreparedStatement t = (PreparedStatement)i$.next();
         t.setShort(arg0, arg1);
      }

   }

   public void setString(int arg0, String arg1) throws SQLException {
      Iterator i$ = this.iterator();

      while(i$.hasNext()) {
         PreparedStatement t = (PreparedStatement)i$.next();
         t.setString(arg0, arg1);
      }

   }

   public void setTime(int arg0, Time arg1) throws SQLException {
      Iterator i$ = this.iterator();

      while(i$.hasNext()) {
         PreparedStatement t = (PreparedStatement)i$.next();
         t.setTime(arg0, arg1);
      }

   }

   public void setTime(int arg0, Time arg1, Calendar arg2) throws SQLException {
      Iterator i$ = this.iterator();

      while(i$.hasNext()) {
         PreparedStatement t = (PreparedStatement)i$.next();
         t.setTime(arg0, arg1, arg2);
      }

   }

   public void setTimestamp(int arg0, Timestamp arg1) throws SQLException {
      Iterator i$ = this.iterator();

      while(i$.hasNext()) {
         PreparedStatement t = (PreparedStatement)i$.next();
         t.setTimestamp(arg0, arg1);
      }

   }

   public void setTimestamp(int arg0, Timestamp arg1, Calendar arg2) throws SQLException {
      Iterator i$ = this.iterator();

      while(i$.hasNext()) {
         PreparedStatement t = (PreparedStatement)i$.next();
         t.setTimestamp(arg0, arg1, arg2);
      }

   }

   public void setURL(int arg0, URL arg1) throws SQLException {
      Iterator i$ = this.iterator();

      while(i$.hasNext()) {
         PreparedStatement t = (PreparedStatement)i$.next();
         t.setURL(arg0, arg1);
      }

   }

   public void setUnicodeStream(int arg0, InputStream arg1, int arg2) throws SQLException {
      Iterator i$ = this.iterator();

      while(i$.hasNext()) {
         PreparedStatement t = (PreparedStatement)i$.next();
         t.setUnicodeStream(arg0, arg1, arg2);
      }

   }

   public void addBatch() throws SQLException {
      Iterator i$ = this.iterator();

      while(i$.hasNext()) {
         PreparedStatement t = (PreparedStatement)i$.next();
         t.addBatch();
      }

   }
}
