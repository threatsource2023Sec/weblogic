package weblogic.servlet.http2;

import java.util.List;

public interface StreamManager {
   Stream getStream(Integer var1);

   Stream createLocalStream() throws StreamException;

   Stream createRemoteStream(Integer var1) throws HTTP2Exception;

   void closeUnprocessedStreams(int var1);

   void setMaxProcessedStreamId(int var1);

   int getMaxProcessedStreamId();

   List getAliveStreams();

   boolean hasAliveStreams();

   void incrementInitSendWindowSize(int var1);

   void incrementInitRecvWindowSize(int var1);

   void removeStream(Integer var1);

   boolean canCreate(Integer var1);

   boolean isRemoteStream(Integer var1);
}
