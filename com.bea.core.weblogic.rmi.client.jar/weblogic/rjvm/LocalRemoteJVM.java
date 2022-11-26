package weblogic.rjvm;

import java.io.IOException;
import java.net.URISyntaxException;
import weblogic.common.internal.PeerInfo;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.invocation.PartitionTable;
import weblogic.kernel.KernelStatus;
import weblogic.protocol.ChannelImpl;
import weblogic.protocol.Protocol;
import weblogic.protocol.ProtocolManager;
import weblogic.protocol.ServerChannel;
import weblogic.rmi.utils.io.RemoteObjectReplacer;
import weblogic.security.acl.internal.AuthenticatedSubject;

public class LocalRemoteJVM extends LocalRJVM {
   private static final DebugLogger LOGGER = DebugLogger.getDebugLogger("DebugLocalRemoteJVM");

   private LocalRemoteJVM() {
   }

   public static LocalRemoteJVM getLocalRemoteJVM() {
      if (KernelStatus.DEBUG && LOGGER.isDebugEnabled()) {
         LOGGER.debug("Got LocalRemoteJVM: " + LocalRemoteJVM.LocalRemoteJVMMaker.localRemoteJVM);
      }

      return LocalRemoteJVM.LocalRemoteJVMMaker.localRemoteJVM;
   }

   private void init() {
      ConnectionManager cm = new LocalConnectionManager(this);
      this.findOrSetConMan(cm);
      PeerInfo peerInfo = this.getPeerInfo();
      this.preDiabloPeer = peerInfo == null || peerInfo.compareTo(PeerInfo.VERSION_DIABLO) < 0;
      JVMID id = JVMID.localID();
      Protocol p = ProtocolManager.getDefaultProtocol();
      this.remoteChannel = new ChannelImpl(id.getAddress(), id.getPort(p), p.getProtocolName());
   }

   public JVMID getID() {
      return JVMID.localRemoteID();
   }

   public PeerInfo getPeerInfo() {
      return PeerInfo.getPeerInfo();
   }

   MsgAbbrevOutputStream getMsgAbbrevOutputStream(ServerChannel channel, String partitionName, String partitionURL, AuthenticatedSubject subject) throws IOException {
      if (partitionName == null) {
         try {
            partitionName = PartitionTable.getInstance().lookup(partitionURL).getPartitionName();
         } catch (URISyntaxException var7) {
            throw new IOException("Can't resolve partitionURL: " + partitionURL);
         }
      }

      LocalConnectionManager localCM = (LocalConnectionManager)this.findOrCreateConMan();
      MsgAbbrevOutputStream maos = new MsgAbbrevOutputStream(localCM, PeerInfo.getPeerInfo(), channel, partitionName);
      maos.setReplacer(RemoteObjectReplacer.getReplacer(PeerInfo.getPeerInfo()));
      maos.setUser(subject);
      return maos;
   }

   // $FF: synthetic method
   LocalRemoteJVM(Object x0) {
      this();
   }

   private static final class LocalRemoteJVMMaker {
      private static final LocalRemoteJVM localRemoteJVM = new LocalRemoteJVM();

      static {
         localRemoteJVM.init();
      }
   }
}
