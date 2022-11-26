package weblogic.jdbc.rmi.internal;

import java.rmi.Remote;
import java.sql.SQLException;
import weblogic.jdbc.common.internal.BlockGetter;
import weblogic.jdbc.common.internal.ReaderBlockGetter;
import weblogic.jdbc.rmi.RmiStatement;

public interface CallableStatement extends PreparedStatement, Remote, java.sql.CallableStatement, RmiStatement {
   BlockGetter getBlockGetter() throws SQLException;

   int registerStream(int var1, int var2) throws SQLException;

   ReaderBlockGetter getReaderBlockGetter() throws SQLException;

   int registerStream(String var1, int var2) throws SQLException;

   void setCharacterStream(String var1, ReaderBlockGetter var2, int var3) throws SQLException;

   void setCharacterStream(String var1, ReaderBlockGetter var2, int var3, int var4) throws SQLException;

   void setCharacterStream(String var1, ReaderBlockGetter var2, int var3, long var4) throws SQLException;

   void setClob(String var1, ReaderBlockGetter var2, int var3) throws SQLException;

   void setClob(String var1, ReaderBlockGetter var2, int var3, long var4) throws SQLException;

   void setNCharacterStream(String var1, ReaderBlockGetter var2, int var3) throws SQLException;

   void setNCharacterStream(String var1, ReaderBlockGetter var2, int var3, long var4) throws SQLException;

   void setNClob(String var1, ReaderBlockGetter var2, int var3) throws SQLException;

   void setNClob(String var1, ReaderBlockGetter var2, int var3, long var4) throws SQLException;

   void setAsciiStream(String var1, BlockGetter var2, int var3) throws SQLException;

   void setAsciiStream(String var1, BlockGetter var2, int var3, int var4) throws SQLException;

   void setAsciiStream(String var1, BlockGetter var2, int var3, long var4) throws SQLException;

   void setBinaryStream(String var1, BlockGetter var2, int var3) throws SQLException;

   void setBinaryStream(String var1, BlockGetter var2, int var3, int var4) throws SQLException;

   void setBinaryStream(String var1, BlockGetter var2, int var3, long var4) throws SQLException;

   void setBlob(String var1, BlockGetter var2, int var3) throws SQLException;

   void setBlob(String var1, BlockGetter var2, int var3, long var4) throws SQLException;

   void setObject(String var1, ReaderBlockGetter var2, int var3) throws SQLException;

   void setObject(String var1, BlockGetter var2, int var3) throws SQLException;

   void setObject(String var1, ReaderBlockGetter var2, int var3, int var4) throws SQLException;

   void setObject(String var1, BlockGetter var2, int var3, int var4) throws SQLException;

   void setObject(String var1, ReaderBlockGetter var2, int var3, int var4, int var5) throws SQLException;

   void setObject(String var1, BlockGetter var2, int var3, int var4, int var5) throws SQLException;
}
