package weblogic.rjvm;

import java.io.IOException;
import java.io.ObjectOutput;
import weblogic.common.internal.PeerInfo;
import weblogic.protocol.ServerChannel;
import weblogic.protocol.ServerIdentity;
import weblogic.utils.AssertionError;

public class ClusterInfoHelper {
   public static ClusterInfo readClusterInfo(MsgAbbrevInputStream in, PeerInfo pi, JVMID id) {
      ClusterInfo ci = null;
      if (pi.getMajor() > 8) {
         try {
            ci = (ClusterInfo)in.readObjectFromPreDiabloPeer();
         } catch (IOException var7) {
            throw new AssertionError(var7);
         } catch (ClassNotFoundException var8) {
            throw new AssertionError(var8);
         }
      } else {
         String protocolName;
         if (pi.getMajor() > 6) {
            protocolName = in.getProtocol().getProtocolName();

            try {
               ci = new ClusterInfo();
               ci.readExternal(in, pi);
               ci.setProtocolName(protocolName);
            } catch (IOException var6) {
               throw new AssertionError(var6);
            }
         } else {
            protocolName = in.getProtocol().getProtocolName();
            ci = id.generate61ClusterInfo(protocolName, false);
         }
      }

      return ci;
   }

   public static void writeClusterInfo(ObjectOutput oo, ServerChannel sc, ServerIdentity server, PeerInfo pi) throws IOException {
      ClusterInfo ci = createClusterInfo(sc, server, pi);
      if (pi != null && pi.getMajor() > 8) {
         oo.writeObject(ci);
      } else {
         ci.writeExternal(oo, (PeerInfo)null);
      }

   }

   private static ClusterInfo createClusterInfo(ServerChannel sc, ServerIdentity server, PeerInfo pi) {
      return pi != null && pi.getMajor() > 8 ? createClusterInfo90Style(sc, server) : createClusterInfo81Style(sc, server);
   }

   private static ClusterInfo createClusterInfo81Style(ServerChannel channel, ServerIdentity server) {
      String addr = channel.getClusterAddress();
      if (server.getDomainName() != null && server.getServerName() != null && addr != null) {
         int protocolMask = 1 << channel.getProtocol().toByte();
         int port = channel.supportsTLS() ? -1 : channel.getPort();
         int sslPort = channel.supportsTLS() ? channel.getPort() : -1;
         return new ClusterInfo(server.getDomainName(), addr, channel.getChannelName(), port, sslPort, -1, protocolMask, channel.getProtocol().getProtocolName(), false);
      } else {
         throw new AssertionError("Bad cluster info for: " + server.toString() + " on " + channel.toString());
      }
   }

   private static ClusterInfo createClusterInfo90Style(ServerChannel sc, ServerIdentity server) {
      return new ClusterInfo90(RJVMEnvironment.getEnvironment().createClusterURL(sc));
   }
}
