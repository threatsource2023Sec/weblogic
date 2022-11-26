package weblogic.servlet.internal;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketException;
import weblogic.protocol.ServerChannel;
import weblogic.utils.io.Chunk;

public interface HttpSocket {
   Socket getSocket();

   InputStream getInputStream();

   OutputStream getOutputStream();

   ServerChannel getServerChannel();

   void closeConnection(Throwable var1);

   boolean handleOnDemandContext(ServletRequestImpl var1, String var2) throws IOException;

   void setHeadChunk(Chunk var1);

   Chunk getHeadChunk();

   void registerForReadEvent();

   void setSocketReadTimeout(int var1) throws SocketException;

   void requeue();
}
