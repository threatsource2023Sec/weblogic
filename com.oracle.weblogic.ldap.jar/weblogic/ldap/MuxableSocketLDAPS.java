package weblogic.ldap;

import com.octetstring.vde.LDAPServer;
import java.io.IOException;
import java.net.Socket;
import weblogic.protocol.ServerChannel;
import weblogic.utils.io.Chunk;

final class MuxableSocketLDAPS extends MuxableSocketLDAP {
   MuxableSocketLDAPS(Chunk headChunk, Socket s, ServerChannel networkChannel) throws IOException {
      super(headChunk, s, networkChannel);
   }

   static void initialize(LDAPServer ldapServer) {
      ProtocolHandlerLDAPS.getProtocolHandler();
   }
}
