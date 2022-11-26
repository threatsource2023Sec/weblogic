package weblogic.jdbc.common.internal;

import java.io.IOException;
import java.rmi.Remote;

public interface JDBCWriter extends Remote {
   void writeBlock(char[] var1) throws IOException;

   void write(char[] var1, int var2, int var3) throws IOException;

   void flush() throws IOException;

   void close() throws IOException;
}
