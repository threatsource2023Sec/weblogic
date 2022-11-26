package weblogic.rmi.spi;

import java.io.IOException;
import java.security.cert.X509Certificate;
import weblogic.protocol.ServerChannel;
import weblogic.rmi.extensions.server.RuntimeMethodDescriptor;
import weblogic.rmi.internal.RuntimeDescriptor;
import weblogic.security.service.ContextHandler;
import weblogic.security.subject.AbstractSubject;

public interface InboundRequest {
   String NO_REQUEST_URL = "";

   MsgInput getMsgInput();

   boolean isCollocated();

   EndPoint getEndPoint();

   ServerChannel getServerChannel();

   void retrieveThreadLocalContext() throws IOException;

   AbstractSubject getSubject();

   RuntimeMethodDescriptor getRuntimeMethodDescriptor(RuntimeDescriptor var1) throws IOException;

   Object getTxContext();

   Object getReplicaInfo() throws IOException;

   OutboundResponse getOutboundResponse() throws IOException;

   void close() throws IOException;

   Object getActivationID() throws IOException;

   Object getContext(int var1) throws IOException;

   X509Certificate[] getCertificateChain();

   ContextHandler getContextHandler();

   String getRequestUrl();
}
