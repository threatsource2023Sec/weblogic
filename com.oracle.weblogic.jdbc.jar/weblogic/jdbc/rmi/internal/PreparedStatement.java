package weblogic.jdbc.rmi.internal;

import java.sql.SQLException;
import weblogic.jdbc.common.internal.BlockGetter;
import weblogic.jdbc.common.internal.ReaderBlockGetter;

public interface PreparedStatement extends Statement, java.sql.PreparedStatement {
   void setAsciiStream(int var1, BlockGetter var2, int var3, int var4) throws SQLException;

   void setUnicodeStream(int var1, BlockGetter var2, int var3, int var4) throws SQLException;

   void setBinaryStream(int var1, BlockGetter var2, int var3, int var4) throws SQLException;

   void setCharacterStream(int var1, ReaderBlockGetter var2, int var3, int var4) throws SQLException;

   void setCharacterStream(int var1, ReaderBlockGetter var2, int var3) throws SQLException;

   void setCharacterStream(int var1, ReaderBlockGetter var2, int var3, long var4) throws SQLException;

   void setClob(int var1, ReaderBlockGetter var2, int var3) throws SQLException;

   void setClob(int var1, ReaderBlockGetter var2, int var3, long var4) throws SQLException;

   void setNCharacterStream(int var1, ReaderBlockGetter var2, int var3) throws SQLException;

   void setNCharacterStream(int var1, ReaderBlockGetter var2, int var3, long var4) throws SQLException;

   void setNClob(int var1, ReaderBlockGetter var2, int var3) throws SQLException;

   void setNClob(int var1, ReaderBlockGetter var2, int var3, long var4) throws SQLException;

   void setAsciiStream(int var1, BlockGetter var2, int var3) throws SQLException;

   void setAsciiStream(int var1, BlockGetter var2, int var3, long var4) throws SQLException;

   void setBinaryStream(int var1, BlockGetter var2, int var3) throws SQLException;

   void setBinaryStream(int var1, BlockGetter var2, int var3, long var4) throws SQLException;

   void setBlob(int var1, BlockGetter var2, int var3) throws SQLException;

   void setBlob(int var1, BlockGetter var2, int var3, long var4) throws SQLException;

   void setObject(int var1, ReaderBlockGetter var2, int var3) throws SQLException;

   void setObject(int var1, BlockGetter var2, int var3) throws SQLException;

   void setObject(int var1, ReaderBlockGetter var2, int var3, int var4) throws SQLException;

   void setObject(int var1, BlockGetter var2, int var3, int var4) throws SQLException;

   void setObject(int var1, ReaderBlockGetter var2, int var3, int var4, int var5) throws SQLException;

   void setObject(int var1, BlockGetter var2, int var3, int var4, int var5) throws SQLException;
}
