package weblogic.jdbc.rowset;

import java.io.InputStream;
import java.io.Reader;
import java.io.Serializable;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Array;
import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.Clob;
import java.sql.Date;
import java.sql.NClob;
import java.sql.Ref;
import java.sql.RowId;
import java.sql.SQLException;
import java.sql.SQLXML;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Calendar;

/** @deprecated */
@Deprecated
class WLParameter implements Serializable {
   private static final long serialVersionUID = 7248152094879848380L;
   static final int DUMMY_INDEX = 0;
   static final int DUMMY_PS = 0;
   static final int ARRAY_PS = 1;
   static final int ASCII_ISTR_PS = 2;
   static final int ASCII_ISTR_LEN_PS = 3;
   static final int BIGD_PS = 4;
   static final int BINSTR_ISTR_PS = 5;
   static final int BINSTR_ISTR_LEN_PS = 6;
   static final int BLOB_PS = 7;
   static final int BLOB_ISTR_PS = 8;
   static final int BLOB_ISTR_LEN_PS = 9;
   static final int BOOL_PS = 10;
   static final int BYTE_PS = 11;
   static final int BYTEARR_PS = 12;
   static final int CHARSTR_READER_PS = 13;
   static final int CHARSTR_READER_LEN_PS = 14;
   static final int CLOB_PS = 15;
   static final int CLOB_READER_PS = 16;
   static final int CLOB_READER_LEN_PS = 17;
   static final int DATE_PS = 18;
   static final int DATE_CAL_PS = 19;
   static final int DOUBLE_PS = 20;
   static final int FLOAT_PS = 21;
   static final int INT_PS = 22;
   static final int LONG_PS = 23;
   static final int NCHARSTR_READER_PS = 24;
   static final int NCHARSTR_READER_LEN_PS = 25;
   static final int NCLOB_PS = 26;
   static final int NCLOB_READER_PS = 27;
   static final int NCLOB_READER_LEN_PS = 28;
   static final int NSTRING_PS = 29;
   static final int NULL_PS = 30;
   static final int NULL_TYPE_PS = 31;
   static final int OBJ_PS = 32;
   static final int OBJ_TYPE_PS = 33;
   static final int OBJ_TYPE_SCALE_PS = 34;
   static final int REF_PS = 35;
   static final int ROWID_PS = 36;
   static final int SHORT_PS = 37;
   static final int SQLXML_PS = 38;
   static final int STRING_PS = 39;
   static final int TIME_PS = 40;
   static final int TIME_CAL_PS = 41;
   static final int TIMESTAMP_PS = 42;
   static final int TIMESTAMP_CAL_PS = 43;
   static final int URL_PS = 44;
   private int index;
   private String name;
   private int type;
   private Object[] params;

   WLParameter(int index, String name, int type, Object[] params) throws SQLException {
      this.index = 0;
      this.name = null;
      this.type = 0;
      this.params = null;
      if (name != null && !name.equals("")) {
         if (index != 0) {
            throw new SQLException("Invalid CallableStatement parameters.");
         }
      } else if (index < 1) {
         throw new SQLException("CallableStatement parameters must be >= 1.");
      }

      this.index = index;
      this.name = name;
      this.type = type;
      this.params = params;
   }

   WLParameter(int index, int type, Object[] params) throws SQLException {
      this(index, (String)null, type, params);
   }

   WLParameter(String name, int type, Object[] params) throws SQLException {
      this(0, name, type, params);
   }

   void setParam(CallableStatement cs) throws SQLException {
      switch (this.type) {
         case 1:
            cs.setArray(this.index, (Array)this.params[0]);
            break;
         case 2:
            if (this.name != null) {
               cs.setAsciiStream(this.name, (InputStream)this.params[0]);
            } else {
               cs.setAsciiStream(this.index, (InputStream)this.params[0]);
            }
            break;
         case 3:
            if (this.name != null) {
               cs.setAsciiStream(this.name, (InputStream)this.params[0], (Integer)this.params[1]);
            } else {
               cs.setAsciiStream(this.index, (InputStream)this.params[0], (Integer)this.params[1]);
            }
            break;
         case 4:
            if (this.name != null) {
               cs.setBigDecimal(this.name, (BigDecimal)this.params[0]);
            } else {
               cs.setBigDecimal(this.index, (BigDecimal)this.params[0]);
            }
            break;
         case 5:
            if (this.name != null) {
               cs.setBinaryStream(this.name, (InputStream)this.params[0]);
            } else {
               cs.setBinaryStream(this.index, (InputStream)this.params[0]);
            }
            break;
         case 6:
            if (this.name != null) {
               cs.setBinaryStream(this.name, (InputStream)this.params[0], (Integer)this.params[1]);
            } else {
               cs.setBinaryStream(this.index, (InputStream)this.params[0], (Integer)this.params[1]);
            }
            break;
         case 7:
            if (this.name != null) {
               cs.setBlob(this.name, (Blob)this.params[0]);
            } else {
               cs.setBlob(this.index, (Blob)this.params[0]);
            }
            break;
         case 8:
            if (this.name != null) {
               cs.setBlob(this.name, (InputStream)this.params[0]);
            } else {
               cs.setBlob(this.index, (InputStream)this.params[0]);
            }
            break;
         case 9:
            if (this.name != null) {
               cs.setBlob(this.name, (InputStream)this.params[0], (Long)this.params[1]);
            } else {
               cs.setBlob(this.index, (InputStream)this.params[0], (Long)this.params[1]);
            }
            break;
         case 10:
            if (this.name != null) {
               cs.setBoolean(this.name, (Boolean)this.params[0]);
            } else {
               cs.setBoolean(this.index, (Boolean)this.params[0]);
            }
            break;
         case 11:
            if (this.name != null) {
               cs.setByte(this.name, (Byte)this.params[0]);
            } else {
               cs.setByte(this.index, (Byte)this.params[0]);
            }
            break;
         case 12:
            if (this.name != null) {
               cs.setBytes(this.name, (byte[])((byte[])this.params[0]));
            } else {
               cs.setBytes(this.index, (byte[])((byte[])this.params[0]));
            }
            break;
         case 13:
            if (this.name != null) {
               cs.setCharacterStream(this.name, (Reader)this.params[0]);
            } else {
               cs.setCharacterStream(this.index, (Reader)this.params[0]);
            }
            break;
         case 14:
            if (this.name != null) {
               cs.setCharacterStream(this.name, (Reader)this.params[0], (Integer)this.params[1]);
            } else {
               cs.setCharacterStream(this.index, (Reader)this.params[0], (Integer)this.params[1]);
            }
            break;
         case 15:
            Clob c = (Clob)this.params[0];
            if (c instanceof RowSetClob) {
               if (this.name != null) {
                  cs.setClob(this.name, c.getCharacterStream());
               } else {
                  cs.setClob(this.index, c.getCharacterStream());
               }
            } else if (this.name != null) {
               cs.setClob(this.name, c);
            } else {
               cs.setClob(this.index, c);
            }
            break;
         case 16:
            if (this.name != null) {
               cs.setClob(this.name, (Reader)this.params[0]);
            } else {
               cs.setClob(this.index, (Reader)this.params[0]);
            }
            break;
         case 17:
            if (this.name != null) {
               cs.setClob(this.name, (Reader)this.params[0], (Long)this.params[1]);
            } else {
               cs.setClob(this.index, (Reader)this.params[0], (Long)this.params[1]);
            }
            break;
         case 18:
            if (this.name != null) {
               cs.setDate(this.name, (Date)this.params[0]);
            } else {
               cs.setDate(this.index, (Date)this.params[0]);
            }
            break;
         case 19:
            if (this.name != null) {
               cs.setDate(this.name, (Date)this.params[0], (Calendar)this.params[1]);
            } else {
               cs.setDate(this.index, (Date)this.params[0], (Calendar)this.params[1]);
            }
            break;
         case 20:
            if (this.name != null) {
               cs.setDouble(this.name, (Double)this.params[0]);
            } else {
               cs.setDouble(this.index, (Double)this.params[0]);
            }
            break;
         case 21:
            if (this.name != null) {
               cs.setFloat(this.name, (Float)this.params[0]);
            } else {
               cs.setFloat(this.index, (Float)this.params[0]);
            }
            break;
         case 22:
            if (this.name != null) {
               cs.setInt(this.name, (Integer)this.params[0]);
            } else {
               cs.setInt(this.index, (Integer)this.params[0]);
            }
            break;
         case 23:
            if (this.name != null) {
               cs.setLong(this.name, (Long)this.params[0]);
            } else {
               cs.setLong(this.index, (Long)this.params[0]);
            }
            break;
         case 24:
            if (this.name != null) {
               cs.setCharacterStream(this.name, (Reader)this.params[0]);
            } else {
               cs.setCharacterStream(this.index, (Reader)this.params[0]);
            }
            break;
         case 25:
            if (this.name != null) {
               cs.setNCharacterStream(this.name, (Reader)this.params[0], (Long)this.params[1]);
            } else {
               cs.setNCharacterStream(this.index, (Reader)this.params[0], (Long)this.params[1]);
            }
            break;
         case 26:
            NClob nc = (NClob)this.params[0];
            if (nc instanceof RowSetNClob) {
               if (this.name != null) {
                  cs.setNClob(this.name, nc.getCharacterStream());
               } else {
                  cs.setNClob(this.index, nc.getCharacterStream());
               }
            } else if (this.name != null) {
               cs.setNClob(this.name, nc);
            } else {
               cs.setNClob(this.index, nc);
            }
            break;
         case 27:
            if (this.name != null) {
               cs.setNClob(this.name, (Reader)this.params[0]);
            } else {
               cs.setNClob(this.index, (Reader)this.params[0]);
            }
            break;
         case 28:
            if (this.name != null) {
               cs.setNClob(this.name, (Reader)this.params[0], (Long)this.params[1]);
            } else {
               cs.setNClob(this.index, (Reader)this.params[0], (Long)this.params[1]);
            }
            break;
         case 29:
            if (this.name != null) {
               cs.setNString(this.name, (String)this.params[0]);
            } else {
               cs.setNString(this.index, (String)this.params[0]);
            }
            break;
         case 30:
            if (this.name != null) {
               cs.setNull(this.name, (Integer)this.params[0]);
            } else {
               cs.setNull(this.index, (Integer)this.params[0]);
            }
            break;
         case 31:
            if (this.name != null) {
               cs.setNull(this.name, (Integer)this.params[0], (String)this.params[1]);
            } else {
               cs.setNull(this.index, (Integer)this.params[0], (String)this.params[1]);
            }
            break;
         case 32:
            if (this.name != null) {
               cs.setObject(this.name, this.params[0]);
            } else {
               cs.setObject(this.index, this.params[0]);
            }
            break;
         case 33:
            if (this.name != null) {
               cs.setObject(this.name, this.params[0], (Integer)this.params[1]);
            } else {
               cs.setObject(this.index, this.params[0], (Integer)this.params[1]);
            }
            break;
         case 34:
            if (this.name != null) {
               cs.setObject(this.name, this.params[0], (Integer)this.params[1], (Integer)this.params[2]);
            } else {
               cs.setObject(this.index, this.params[0], (Integer)this.params[1], (Integer)this.params[2]);
            }
            break;
         case 35:
            cs.setRef(this.index, (Ref)this.params[0]);
            break;
         case 36:
            if (this.name != null) {
               cs.setRowId(this.name, (RowId)this.params[0]);
            } else {
               cs.setRowId(this.index, (RowId)this.params[0]);
            }
            break;
         case 37:
            if (this.name != null) {
               cs.setShort(this.name, (Short)this.params[0]);
            } else {
               cs.setShort(this.index, (Short)this.params[0]);
            }
            break;
         case 38:
            if (this.name != null) {
               cs.setSQLXML(this.name, (SQLXML)this.params[0]);
            } else {
               cs.setSQLXML(this.index, (SQLXML)this.params[0]);
            }
            break;
         case 39:
            if (this.name != null) {
               cs.setString(this.name, (String)this.params[0]);
            } else {
               cs.setString(this.index, (String)this.params[0]);
            }
            break;
         case 40:
            if (this.name != null) {
               cs.setTime(this.name, (Time)this.params[0]);
            } else {
               cs.setTime(this.index, (Time)this.params[0]);
            }
            break;
         case 41:
            if (this.name != null) {
               cs.setTime(this.name, (Time)this.params[0], (Calendar)this.params[1]);
            } else {
               cs.setTime(this.index, (Time)this.params[0], (Calendar)this.params[1]);
            }
            break;
         case 42:
            if (this.name != null) {
               cs.setTimestamp(this.index, (Timestamp)this.params[0]);
            } else {
               cs.setTimestamp(this.index, (Timestamp)this.params[0]);
            }
            break;
         case 43:
            if (this.name != null) {
               cs.setTimestamp(this.index, (Timestamp)this.params[0], (Calendar)this.params[1]);
            } else {
               cs.setTimestamp(this.index, (Timestamp)this.params[0], (Calendar)this.params[1]);
            }
            break;
         case 44:
            if (this.name != null) {
               cs.setURL(this.name, (URL)this.params[0]);
            } else {
               cs.setURL(this.index, (URL)this.params[0]);
            }
            break;
         default:
            throw new AssertionError("Unexpected type: " + this.type);
      }

   }

   Object getObject() {
      return this.params;
   }

   int getIndex() {
      return this.index;
   }

   String getName() {
      return this.name;
   }

   int getType() {
      return this.type;
   }

   Object[] getParams() {
      return this.params;
   }
}
