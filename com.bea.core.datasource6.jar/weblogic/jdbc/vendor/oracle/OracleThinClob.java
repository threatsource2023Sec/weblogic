package weblogic.jdbc.vendor.oracle;

import java.io.OutputStream;
import java.io.Writer;
import java.sql.SQLException;

/** @deprecated */
@Deprecated
public interface OracleThinClob {
   OutputStream getAsciiOutputStream() throws SQLException;

   Writer getCharacterOutputStream() throws SQLException;

   int getBufferSize() throws SQLException;

   int getChunkSize() throws SQLException;

   int getChars(long var1, int var3, char[] var4) throws SQLException;

   int putChars(long var1, char[] var3) throws SQLException;

   int putString(long var1, String var3) throws SQLException;

   void truncate(long var1) throws SQLException;

   void trim(long var1) throws SQLException;
}
