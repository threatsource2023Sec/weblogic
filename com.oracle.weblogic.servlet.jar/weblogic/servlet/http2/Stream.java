package weblogic.servlet.http2;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import weblogic.servlet.http2.frame.DataFrame;
import weblogic.servlet.http2.frame.FrameType;
import weblogic.servlet.http2.frame.HeadersFrame;
import weblogic.servlet.internal.ReadListenerStateContext;

public interface Stream {
   Integer getId();

   long getLastAccessTime();

   void close(HTTP2Exception var1);

   void close();

   boolean isClosed();

   int incrementInitRecvWindowSize(int var1) throws ConnectionException;

   int incrementRecvWindowSize(int var1) throws StreamException;

   int incrementInitSendWindowSize(int var1) throws ConnectionException;

   int incrementSendWindowSize(int var1) throws StreamException;

   void reset(StreamException var1) throws HTTP2Exception;

   void reset(int var1) throws HTTP2Exception, IOException;

   void initRequestData(DataFrame var1) throws ConnectionException;

   boolean headerStart();

   void checkOnReceiving(FrameType var1) throws HTTP2Exception;

   void checkOnSending(FrameType var1) throws HTTP2Exception;

   void receivedHeaders(HeadersFrame var1) throws HTTP2Exception;

   void receivedEndOfStream();

   void handleOnContainer();

   void closeIfIdle();

   void receivedReset(boolean var1) throws ConnectionException;

   void setGoAwayCallback(GoAwayCallBack var1);

   int sendHeaders(List var1, boolean var2);

   int sendTrailers(Map var1);

   void completeDataSend() throws IOException;

   boolean isPushSupported();

   int sendPushPromise(List var1, int var2);

   void sendPushPromise();

   HTTP2Connection getHTTP2Connection();

   void sendWindowUpdate(int var1);

   void setReadListener(ReadListenerStateContext var1);

   void notifyErrorIfNonBlocking(Throwable var1);
}
