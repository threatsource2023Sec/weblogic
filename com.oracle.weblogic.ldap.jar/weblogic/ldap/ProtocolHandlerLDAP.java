package weblogic.ldap;

import java.io.IOException;
import java.net.Socket;
import weblogic.protocol.Protocol;
import weblogic.protocol.ProtocolHandler;
import weblogic.protocol.ProtocolManager;
import weblogic.protocol.ServerChannel;
import weblogic.server.channels.ServerChannelImpl;
import weblogic.socket.MuxableSocket;
import weblogic.utils.io.Chunk;

class ProtocolHandlerLDAP implements ProtocolHandler {
   private static ProtocolHandler theOne = new ProtocolHandlerLDAP();
   private static final String PROTOCOL_NAME = "LDAP";
   public static final Protocol PROTOCOL_LDAP = ProtocolManager.createProtocol((byte)10, "ldap", "ldap", false, getProtocolHandler());
   private static final byte ASN1_SEQUENCE_DEFINITE_LENGTH = 48;
   private static final byte BER_ENCODED_INTEGER = 2;
   private static final byte BER_ENCODED_APPLICATION_0 = 96;
   private static final int DISCRIMINATION_LENGTH = 11;

   public static ProtocolHandler getProtocolHandler() {
      return theOne;
   }

   public ServerChannel getDefaultServerChannel() {
      return ProtocolHandlerLDAP.ChannelInitializer.CHANNEL;
   }

   public int getHeaderLength() {
      return 11;
   }

   public int getPriority() {
      return 2;
   }

   public Protocol getProtocol() {
      return PROTOCOL_LDAP;
   }

   public boolean claimSocket(Chunk head) {
      if (head.end < 11) {
         return false;
      } else {
         byte[] buf = head.buf;
         int pos = 0;
         if (buf[pos++] != 48) {
            return false;
         } else {
            int length = false;
            int length;
            if ((buf[pos] & 128) == 0) {
               length = buf[pos++] & 127;
            } else {
               int numOctets = buf[pos++] & 127;
               if (numOctets > 2) {
                  return false;
               }

               int length = buf[pos++];
               if (numOctets == 2) {
                  length = (length << 8) + (255 & buf[pos++]);
               }
            }

            if (buf[pos++] != 2) {
               return false;
            } else if ((buf[pos] & 128) == 128) {
               return false;
            } else {
               int messageIDlen = buf[pos++];
               if (messageIDlen > 4) {
                  return false;
               } else {
                  pos += messageIDlen;
                  return buf[pos] == 96;
               }
            }
         }
      }
   }

   public MuxableSocket createSocket(Chunk head, Socket s, ServerChannel networkChannel) throws IOException {
      return new MuxableSocketLDAP(head, s, networkChannel);
   }

   private static final class ChannelInitializer {
      private static final ServerChannel CHANNEL;

      static {
         CHANNEL = ServerChannelImpl.createDefaultServerChannel(ProtocolHandlerLDAP.PROTOCOL_LDAP);
      }
   }
}
