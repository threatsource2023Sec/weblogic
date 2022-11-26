package weblogic.jdbc.vendor.oracle;

import java.io.OutputStream;
import java.sql.SQLException;

/** @deprecated */
@Deprecated
public interface OracleThinBlob {
   OutputStream getBinaryOutputStream() throws SQLException;

   int getBufferSize() throws SQLException;

   int getChunkSize() throws SQLException;

   int putBytes(long var1, byte[] var3) throws SQLException;

   void truncate(long var1) throws SQLException;

   void trim(long var1) throws SQLException;
}
