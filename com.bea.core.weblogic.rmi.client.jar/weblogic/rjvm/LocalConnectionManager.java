package weblogic.rjvm;

import java.io.IOException;
import java.net.InetAddress;
import java.security.AccessController;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.invocation.ComponentInvocationContextManager;
import weblogic.invocation.ManagedInvocationContext;
import weblogic.kernel.KernelStatus;
import weblogic.protocol.OutgoingMessage;
import weblogic.protocol.ServerChannel;
import weblogic.rmi.facades.RmiInvocationFacade;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.utils.io.Chunk;

public class LocalConnectionManager extends ConnectionManager {
   private static final DebugLogger LOGGER = DebugLogger.getDebugLogger("DebugLocalRemoteJVM");
   private static final AuthenticatedSubject KERNEL_ID = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());

   LocalConnectionManager(RJVMImpl rjvm) {
      super(rjvm);
   }

   protected MsgAbbrevJVMConnection getConnectionInPairedConnTable(String remotePartitionName, byte protocolNum) {
      if (!KernelStatus.isServer()) {
         throw new UnsupportedOperationException("This should only be called on server side!");
      } else {
         String localPN = ComponentInvocationContextManager.getInstance().getCurrentComponentInvocationContext().getPartitionName();
         MsgAbbrevJVMConnection connection = this.getConnectionInPairedConnTable(remotePartitionName, localPN, protocolNum);
         if (connection != null) {
            return connection;
         } else {
            connection = new MsgAbbrevJVMConnection() {
               ServerChannel ch;

               public ServerChannel getChannel() {
                  if (this.ch == null) {
                     this.ch = LocalConnectionManager.this.thisRJVM.ensureChannel((ServerChannel)null, (byte)101);
                  }

                  return this.ch;
               }

               public InetAddress getInetAddress() {
                  throw new UnsupportedOperationException("Should NEVER call this!");
               }

               public InetAddress getLocalAddress() {
                  throw new UnsupportedOperationException("Should NEVER call this!");
               }

               public int getLocalPort() {
                  throw new UnsupportedOperationException("Should NEVER call this!");
               }

               public void connect(String host, InetAddress address, int port, int connectTimeout) throws IOException {
                  throw new UnsupportedOperationException("Should NEVER call this!");
               }

               protected void sendMsg(OutgoingMessage msg) throws IOException {
                  if (KernelStatus.DEBUG && LocalConnectionManager.LOGGER.isDebugEnabled()) {
                     LocalConnectionManager.LOGGER.debug("sendMsg(" + msg + ")");
                  }

                  try {
                     Chunk data = msg.getChunks();
                     MsgAbbrevInputStream mais = new MsgAbbrevInputStream(LocalConnectionManager.this);
                     mais.init(data, this);
                     this.getDispatcher().dispatch(this, mais);
                  } catch (Exception var4) {
                     throw new RuntimeException("fail to sendMsg by local connection " + this, var4);
                  }
               }

               public void close() {
                  throw new UnsupportedOperationException("Should NEVER call this!");
               }
            };
            connection.init(RJVMEnvironment.getEnvironment().getAbbrevTableSize(), 19, -1, remotePartitionName, (String)null, localPN);
            return connection.setDispatcher(this, false);
         }
      }
   }

   void handleRJVM(MsgAbbrevJVMConnection connection, MsgAbbrevInputStream inputStream) {
      String partition = connection.getLocalPartitionName();

      try {
         ManagedInvocationContext mic = RmiInvocationFacade.setPartitionName(KERNEL_ID, partition);
         Throwable var5 = null;

         try {
            if (KernelStatus.DEBUG && LOGGER.isDebugEnabled()) {
               LOGGER.debug("pushed CIC " + partition);
            }

            this.thisRJVM.dispatch(inputStream);
         } catch (Throwable var21) {
            var5 = var21;
            throw var21;
         } finally {
            if (mic != null) {
               if (var5 != null) {
                  try {
                     mic.close();
                  } catch (Throwable var20) {
                     var5.addSuppressed(var20);
                  }
               } else {
                  mic.close();
               }
            }

         }
      } finally {
         if (KernelStatus.DEBUG && LOGGER.isDebugEnabled()) {
            LOGGER.debug("popped CIC " + partition);
         }

      }

   }

   void handleIdentifyRequest(MsgAbbrevJVMConnection connection, MsgAbbrevInputStream inputStream) {
   }

   void handleIdentifyResponse(MsgAbbrevJVMConnection connection, MsgAbbrevInputStream inputStream) {
   }

   void handlePeerGone(MsgAbbrevJVMConnection connection, MsgAbbrevInputStream inputStream) {
   }
}
