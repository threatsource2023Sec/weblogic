package weblogic.socket;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.net.SocketException;
import java.nio.ByteBuffer;
import weblogic.utils.io.Chunk;

public interface MuxableSocket {
   byte[] getBuffer();

   int getBufferOffset();

   void incrementBufferOffset(int var1) throws MaxMessageSizeExceededException;

   boolean isMessageComplete();

   void dispatch();

   Socket getSocket();

   boolean closeSocketOnError();

   InputStream getSocketInputStream();

   void setSoTimeout(int var1) throws SocketException;

   void hasException(Throwable var1);

   void endOfStream();

   boolean timeout();

   boolean requestTimeout();

   int getIdleTimeoutMillis();

   int getCompleteMessageTimeoutMillis();

   void setSocketFilter(MuxableSocket var1);

   MuxableSocket getSocketFilter();

   void setSocketInfo(SocketInfo var1);

   SocketInfo getSocketInfo();

   boolean supportsScatteredRead();

   long read(NIOConnection var1) throws IOException;

   void incrementBufferOffset(Chunk var1, int var2) throws MaxMessageSizeExceededException;

   ByteBuffer getByteBuffer();
}
