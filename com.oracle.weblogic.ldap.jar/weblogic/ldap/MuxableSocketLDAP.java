package weblogic.ldap;

import com.octetstring.vde.ConnectionHandler;
import com.octetstring.vde.LDAPServer;
import java.io.IOException;
import java.net.Socket;
import weblogic.protocol.ServerChannel;
import weblogic.socket.AbstractMuxableSocket;
import weblogic.socket.SocketMuxer;
import weblogic.socket.WeblogicSocket;
import weblogic.utils.io.Chunk;
import weblogic.utils.io.ChunkedInputStream;

class MuxableSocketLDAP extends AbstractMuxableSocket {
   private static LDAPServer ldapServer;
   private final ConnectionHandler connectionHandler;

   MuxableSocketLDAP(Chunk headChunk, Socket s, ServerChannel networkChannel) throws IOException {
      super(headChunk, s, networkChannel);
      LDAPSocket ldapSocket = new LDAPSocket(this.getSocket(), this);
      this.connectionHandler = ldapServer.createConnectionHandler(ldapSocket);
      this.connectionHandler.setExternalExecutor(EmbeddedLDAP.getEmbeddedLDAP());
   }

   static void initialize(LDAPServer ldapServer) {
      ProtocolHandlerLDAP.getProtocolHandler();
      MuxableSocketLDAP.ldapServer = ldapServer;
   }

   protected int getHeaderLength() {
      return 2;
   }

   protected int getMessageLength() {
      byte octet = this.getHeaderByte(1);
      if ((octet & 128) == 0) {
         return 2 + (octet & 127);
      } else {
         int headerLength = 2 + (octet & 127);
         if (this.availBytes < headerLength) {
            return -1;
         } else {
            int msgLength = 0;

            for(int i = 2; i < headerLength; ++i) {
               msgLength <<= 8;
               msgLength += this.getHeaderByte(i) & 255;
            }

            return msgLength + headerLength;
         }
      }
   }

   public void dispatch(Chunk chunks) {
      try {
         ChunkedInputStream cis = new ChunkedInputStream(chunks, 0);
         if (!this.connectionHandler.dispatch(cis)) {
            this.close();
            return;
         }
      } catch (Throwable var3) {
         SocketMuxer.getMuxer().deliverHasException(this.getSocketFilter(), var3);
         this.close();
      }

   }

   public int getIdleTimeoutMillis() {
      return 0;
   }

   public class LDAPSocket extends WeblogicSocket {
      private MuxableSocketLDAP muxableSocketLDAP;

      LDAPSocket(Socket s, MuxableSocketLDAP msl) {
         super(s);
         this.muxableSocketLDAP = msl;
      }

      public final void close() throws IOException {
         if (this.muxableSocketLDAP.getSocketInfo() == null && this.muxableSocketLDAP.getSocketFilter().getSocketInfo() == null) {
            super.close();
            this.muxableSocketLDAP.endOfStream();
         } else {
            SocketMuxer.getMuxer().deliverEndOfStream(this.muxableSocketLDAP);
         }

      }
   }
}
