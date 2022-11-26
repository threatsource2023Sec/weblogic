package weblogic.jdbc.common.internal;

import java.io.IOException;
import java.io.Reader;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ReaderBlockGetter extends Remote {
   char[] getBlock(int var1) throws RemoteException;

   int register(Reader var1, int var2);

   int getBlockSize();

   Reader getReader(int var1);

   boolean markSupported(int var1);

   void mark(int var1, int var2) throws IOException;

   boolean ready(int var1) throws IOException;

   void reset(int var1) throws IOException;

   void close(int var1);

   void close();
}
