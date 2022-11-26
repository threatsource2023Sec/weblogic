package weblogic.jdbc.rmi.internal;

import java.rmi.Remote;
import java.sql.SQLException;
import weblogic.jdbc.common.internal.BlockGetter;
import weblogic.jdbc.common.internal.ReaderBlockGetter;

public interface ResultSet extends Remote, java.sql.ResultSet {
   BlockGetter getBlockGetter() throws SQLException;

   ReaderBlockGetter getReaderBlockGetter() throws SQLException;

   int registerStream(int var1, int var2) throws SQLException;

   int registerStream(String var1, int var2) throws SQLException;

   boolean isRowCaching() throws SQLException;

   ResultSetRowCache getNextRowCache() throws SQLException;

   ResultSetMetaDataCache getMetaDataCache() throws SQLException;

   void updateNClob(int var1, ReaderBlockGetter var2, int var3) throws SQLException;

   void updateNClob(int var1, ReaderBlockGetter var2, int var3, long var4) throws SQLException;

   void updateClob(int var1, ReaderBlockGetter var2, int var3) throws SQLException;

   void updateClob(int var1, ReaderBlockGetter var2, int var3, long var4) throws SQLException;

   void updateCharacterStream(int var1, ReaderBlockGetter var2, int var3) throws SQLException;

   void updateCharacterStream(int var1, ReaderBlockGetter var2, int var3, int var4) throws SQLException;

   void updateCharacterStream(int var1, ReaderBlockGetter var2, int var3, long var4) throws SQLException;

   void updateNCharacterStream(int var1, ReaderBlockGetter var2, int var3) throws SQLException;

   void updateNCharacterStream(int var1, ReaderBlockGetter var2, int var3, long var4) throws SQLException;

   void updateObject(int var1, ReaderBlockGetter var2, int var3) throws SQLException;

   void updateObject(int var1, ReaderBlockGetter var2, int var3, int var4) throws SQLException;

   void updateAsciiStream(int var1, BlockGetter var2, int var3) throws SQLException;

   void updateAsciiStream(int var1, BlockGetter var2, int var3, int var4) throws SQLException;

   void updateAsciiStream(int var1, BlockGetter var2, int var3, long var4) throws SQLException;

   void updateBinaryStream(int var1, BlockGetter var2, int var3) throws SQLException;

   void updateBinaryStream(int var1, BlockGetter var2, int var3, int var4) throws SQLException;

   void updateBinaryStream(int var1, BlockGetter var2, int var3, long var4) throws SQLException;

   void updateBlob(int var1, BlockGetter var2, int var3) throws SQLException;

   void updateBlob(int var1, BlockGetter var2, int var3, long var4) throws SQLException;

   void updateObject(int var1, BlockGetter var2, int var3) throws SQLException;

   void updateObject(int var1, BlockGetter var2, int var3, int var4) throws SQLException;
}
