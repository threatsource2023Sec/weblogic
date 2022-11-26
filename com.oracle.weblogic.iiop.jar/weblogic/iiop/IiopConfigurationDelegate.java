package weblogic.iiop;

import java.io.IOException;
import org.jvnet.hk2.annotations.Contract;
import weblogic.iiop.ior.IOR;
import weblogic.protocol.Protocol;
import weblogic.protocol.ServerChannel;
import weblogic.rmi.spi.HostID;
import weblogic.security.acl.internal.AuthenticatedSubject;

@Contract
public interface IiopConfigurationDelegate {
   int getSslListenPort();

   boolean isClientCertificateEnforced();

   String[] getCiphersuites();

   boolean isSslChannelEnabled();

   HostID getHostID(IOR var1);

   Object getActivationID(IOR var1);

   int getObjectId(IOR var1);

   boolean isLocal(IOR var1);

   boolean isIiopEnabled();

   AuthenticatedSubject getSecureConnectionDefaultSubject(AuthenticatedSubject var1);

   int getPendingResponseTimeout();

   int getKeepAliveTimeout();

   int getBackoffInterval();

   void runAsynchronously(Runnable var1);

   EndPoint createEndPoint(Connection var1);

   boolean mayLoadRemoteClass(IOR var1);

   ServerChannel getServerChannel(AuthenticatedSubject var1, Protocol var2, String var3) throws IOException;

   ServerChannel getLocalServerChannel(Protocol var1);

   boolean isRemoteAnonymousRMIIIOPEnabled();
}
