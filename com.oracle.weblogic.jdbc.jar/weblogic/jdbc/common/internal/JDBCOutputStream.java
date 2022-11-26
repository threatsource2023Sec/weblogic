package weblogic.jdbc.common.internal;

import java.io.IOException;
import java.rmi.Remote;

public interface JDBCOutputStream extends Remote {
   void writeBlock(byte[] var1) throws IOException;

   void write(int var1) throws IOException;

   void flush() throws IOException;

   void close() throws IOException;
}
