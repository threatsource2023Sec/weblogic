package weblogic.jdbc.rmi.internal;

import java.rmi.Remote;
import java.sql.SQLException;
import weblogic.jdbc.common.internal.BlockGetter;

public interface OracleTBlob extends Remote, java.sql.Blob {
   BlockGetter getBlockGetter() throws SQLException;

   int registerStream(int var1) throws SQLException;

   void internalClose();
}
