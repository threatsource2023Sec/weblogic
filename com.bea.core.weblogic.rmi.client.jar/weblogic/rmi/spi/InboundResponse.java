package weblogic.rmi.spi;

import java.io.IOException;
import weblogic.rmi.cluster.PiggybackResponse;

public interface InboundResponse {
   MsgInput getMsgInput();

   Object unmarshalReturn() throws Throwable;

   void retrieveThreadLocalContext() throws IOException;

   void retrieveThreadLocalContext(boolean var1) throws IOException;

   Object getTxContext();

   PiggybackResponse getReplicaInfo() throws IOException;

   Object getActivatedPinnedRef() throws IOException;

   Object getContext(int var1) throws IOException;

   void close() throws IOException;
}
