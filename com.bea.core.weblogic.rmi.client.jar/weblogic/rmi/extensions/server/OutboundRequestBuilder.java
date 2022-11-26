package weblogic.rmi.extensions.server;

import java.io.IOException;
import weblogic.rmi.spi.OutboundRequest;

public interface OutboundRequestBuilder {
   OutboundRequest getOutboundRequest(RuntimeMethodDescriptor var1, int var2, Object var3, String var4) throws IOException;
}
