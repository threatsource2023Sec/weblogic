package weblogic.jdbc.common.internal;

import java.io.IOException;
import java.io.InputStream;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface BlockGetter extends Remote {
   byte[] getBlock(int var1) throws RemoteException;

   int register(InputStream var1, int var2);

   InputStream getStream(int var1);

   boolean markSupported(int var1);

   void mark(int var1, int var2);

   int available(int var1) throws IOException;

   void reset(int var1) throws IOException;

   void close(int var1);

   void close();
}
