package weblogic.rmi.extensions.server;

import java.io.IOException;
import java.lang.reflect.Method;
import java.rmi.Remote;
import weblogic.rmi.spi.Channel;
import weblogic.rmi.spi.HostID;
import weblogic.rmi.spi.OutboundRequest;

public interface RemoteReference {
   OutboundRequest getOutboundRequest(RuntimeMethodDescriptor var1, String var2, String var3) throws IOException;

   /** @deprecated */
   @Deprecated
   OutboundRequest getOutboundRequest(RuntimeMethodDescriptor var1, String var2) throws IOException;

   Object invoke(Remote var1, RuntimeMethodDescriptor var2, Object[] var3, Method var4) throws Throwable;

   int getObjectID();

   HostID getHostID();

   Channel getChannel();

   String getCodebase();

   void setRequestTimedOut(boolean var1);

   boolean hasRequestTimedOut();
}
