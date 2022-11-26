package weblogic.jdbc.rmi.internal;

import java.rmi.Remote;
import java.sql.SQLException;
import weblogic.jdbc.common.internal.BlockGetter;
import weblogic.jdbc.common.internal.ReaderBlockGetter;

public interface SQLXML extends Remote, java.sql.SQLXML {
   BlockGetter getBlockGetter() throws SQLException;

   ReaderBlockGetter getReaderBlockGetter() throws SQLException;

   int registerStream(int var1) throws SQLException;

   void internalClose();
}
