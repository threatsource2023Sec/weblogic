package weblogic.buzzmessagebus;

import java.io.IOException;
import org.jvnet.hk2.annotations.Contract;

@Contract
public interface BuzzHTTP {
   byte[] getBuffer();

   int getBufferOffset();

   void incrementBufferOffset(int var1) throws IOException;

   boolean isMessageComplete();

   void dispatch();

   int getCompleteMessageTimeoutMillis();

   void hasException(Throwable var1);

   boolean isServletResponseCommitCalled();

   boolean isDispatchOnRequestData();
}
