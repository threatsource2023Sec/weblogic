package weblogic.security.SSL;

import javax.net.ssl.HandshakeCompletedListener;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.SSLSocket;

public abstract class WeblogicSSLEngine extends SSLEngine {
   public abstract void setAssociatedSSLSocket(SSLSocket var1);

   public abstract SSLSocket getAssociatedSSLSocket();

   public abstract void addHandshakeCompletedListener(HandshakeCompletedListener var1);

   public abstract void removeHandshakeCompletedListener(HandshakeCompletedListener var1);

   public abstract String getALPNProtocol();
}
