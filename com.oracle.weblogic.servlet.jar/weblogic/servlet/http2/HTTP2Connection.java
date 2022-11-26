package weblogic.servlet.http2;

import weblogic.servlet.http2.frame.PingFrame;
import weblogic.servlet.http2.hpack.HpackEncoder;

public interface HTTP2Connection {
   boolean isConnectionClosed();

   boolean isOnGoAway();

   void closeConnection(Throwable var1);

   void closeTimeoutStreams(int var1);

   void sendBytes(byte[] var1);

   void tryTeminateConnection();

   void setMaxProcessedStreamId(int var1);

   RemoteSettings getRemoteSettings();

   LocalSettings getLocalSettings();

   void setInitSendWindowSizeForStream(int var1);

   int getInitSendWindowSizeForStream();

   void setInitRecvWindowSizeForStream(int var1);

   int getInitRecvWindowSizeForStream();

   StreamManager getStreamManager();

   int incrementSendWindowSize(int var1);

   int incrementRecvWindowSize(int var1);

   int reserveSendWindowSize(int var1);

   boolean isSecure();

   HpackEncoder getHpackEncoder();

   void receivedPing(PingFrame var1);

   boolean hasSendWindow();
}
