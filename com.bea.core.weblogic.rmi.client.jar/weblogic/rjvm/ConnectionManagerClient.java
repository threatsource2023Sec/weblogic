package weblogic.rjvm;

import weblogic.common.internal.PeerInfo;
import weblogic.rmi.extensions.UnrecoverableConnectionException;

public final class ConnectionManagerClient extends ConnectionManager {
   public ConnectionManagerClient(RJVMImpl rjvm) {
      super(rjvm);
   }

   final void handleRJVM(MsgAbbrevJVMConnection connection, MsgAbbrevInputStream inputStream) {
      JVMMessage header = inputStream.getMessageHeader();
      if (!JVMID.localID().equals(header.dest)) {
         this.shouldNeverHappen(connection, "Client received a message for the wrong JVM: '" + header.dest + '\'');
      } else if (this.thisRJVM == null) {
         this.shouldNeverHappen(connection, "Client received a message over an uninitialized connection: '" + header + '\'');
      } else {
         if (this.thisRJVM.getID().equals(header.src)) {
            this.thisRJVM.dispatch(inputStream);
         } else {
            RJVMImpl theRJVM = RJVMManager.getRJVMManager().findRemote(header.src);
            if (theRJVM == null) {
               this.shouldNeverHappen(connection, "Client received a routed message from an unknown JVM: '" + header.src + '\'');
            } else {
               theRJVM.dispatch(inputStream);
            }
         }

      }
   }

   final void handleIdentifyRequest(MsgAbbrevJVMConnection connection, MsgAbbrevInputStream inputStream) {
      JVMMessage header = inputStream.getMessageHeader();
      if (!JVMID.localID().equals(header.dest)) {
         this.shouldNeverHappen(connection, "Client received a CMD_IDENTIFY_REQUEST for the wrong JVM: '" + header.dest + '\'');
      } else if (this.thisRJVM == null) {
         this.shouldNeverHappen(connection, "Client received a CMD_IDENTIFY_REQUEST over an uninitialized connection.");
      } else if (this.thisRJVM.getID().equals(header.src)) {
         this.shouldNeverHappen(connection, "Client received an unrouted CMD_IDENTIFY_REQUEST through an established connection");
      } else {
         MsgAbbrevOutputStream outputStream = this.createIdentifyMsg(header.src, header.QOS, JVMMessage.Command.CMD_IDENTIFY_RESPONSE, connection.getChannel(), (PeerInfo)null);
         connection.sendMsg(outputStream);
         int remotePeriodLength = readRemotePeriodLength(inputStream);
         byte[] sharedSecret = readPublickey(inputStream);
         PeerInfo peerInfo = readPeerInfo(inputStream);
         RJVMImpl theRJVM = RJVMManager.getRJVMManager().findOrCreateRemote(header.src);
         peerInfo.setIsServer(theRJVM.getID().isServer());
         theRJVM.completeConnectionSetup(remotePeriodLength, sharedSecret, peerInfo, connection, header.QOS);
         if (!this.thisRJVM.getID().equals(theRJVM.getID())) {
            theRJVM.findOrCreateConManRouter(this);
         }

      }
   }

   final void handleIdentifyResponse(MsgAbbrevJVMConnection connection, MsgAbbrevInputStream inputStream) {
      JVMMessage header = inputStream.getMessageHeader();
      JVMID me = JVMID.localID();
      JVMID src = header.src;
      JVMID dest = header.dest;
      if (!me.equals(dest)) {
         this.shouldNeverHappen(connection, "Client received a CMD_IDENTIFY_RESPONSE for the wrong JVM: '" + dest + '\'');
      }

      if (dest.getRouter() != null && (me.getRouter() == null || !dest.getRouter().equals(me.getRouter()))) {
         me.setRouter(dest.getRouter());
      }

      int remotePeriodLength = readRemotePeriodLength(inputStream);
      byte[] sharedSecret = readPublickey(inputStream);
      PeerInfo peerInfo = readPeerInfo(inputStream);
      if (peerInfo != null && peerInfo.compareTo(PeerInfo.VERSION_DIABLO) >= 0) {
         src.cleanupPorts();
      }

      RJVMImpl theRJVM = (RJVMImpl)RJVMManager.getRJVMManager().findOrCreate(src);
      if (peerInfo != null) {
         peerInfo.setIsServer(theRJVM.getID().isServer());
      }

      theRJVM.completeConnectionSetup(remotePeriodLength, sharedSecret, peerInfo, connection, header.QOS);
      ConnectionManager theConMan = null;
      ConnectionManager bootstrapConMan;
      if (this.thisRJVM == null) {
         this.thisRJVM = theRJVM;
         theConMan = this.thisRJVM.findOrSetConMan(this);
         bootstrapConMan = connection.getDispatcher();
         bootstrapConMan.bootstrapRJVM = this.thisRJVM;
         connection.setDispatcher(theConMan, true);
         if (header.invokableId == 7938) {
            this.thisRJVM.convertedToAdminQOS = true;
         }

         setAppletRouter(theConMan);
      } else if (this.thisRJVM.getID().equals(src)) {
         theConMan = this.thisRJVM.findOrCreateConMan();
         bootstrapConMan = theConMan;
         setAppletRouter(theConMan);
      } else {
         bootstrapConMan = theRJVM.findOrCreateConMan();
      }

      if (theConMan != null) {
         connection.setClusterInfo(readClusterInfo(inputStream, peerInfo, theRJVM.getID()));
      }

      if (bootstrapConMan != null) {
         bootstrapConMan.bootstrapResponseReceived = true;
         synchronized(bootstrapConMan.bootstrapResult) {
            if (this.bootstrapRJVM == null) {
               this.bootstrapRJVM = theRJVM;
            }

            bootstrapConMan.bootstrapResult.notify();
         }
      }

   }

   final void handlePeerGone(MsgAbbrevJVMConnection connection, MsgAbbrevInputStream inputStream) {
      ConnectionManager bootstrapConMan;
      if (this.thisRJVM == null) {
         bootstrapConMan = connection.getDispatcher();
         if (bootstrapConMan != null) {
            bootstrapConMan.bootstrapResponseReceived = true;
            this.bootstrapRJVM = null;
            synchronized(bootstrapConMan.bootstrapResult) {
               bootstrapConMan.bootstrapResult.notify();
            }
         }
      } else {
         JVMMessage header = inputStream.getMessageHeader();
         if (!JVMID.localID().equals(header.dest)) {
            this.shouldNeverHappen(connection, "Client received a CMD_PEER_GONE for the wrong JVM: '" + header.dest + '\'');
            return;
         }

         String message = "Peer requested connection shutdown";
         if (this.thisRJVM.getID().equals(header.src)) {
            bootstrapConMan = this.thisRJVM.findOrCreateConMan();
            this.thisRJVM.peerGone(new UnrecoverableConnectionException(message));
         } else {
            RJVMImpl theRJVM = RJVMManager.getRJVMManager().findRemote(header.src);
            if (theRJVM != null) {
               theRJVM.findOrCreateConMan();
               theRJVM.peerGone(new PeerGoneException(message));
            }
         }
      }

   }
}
