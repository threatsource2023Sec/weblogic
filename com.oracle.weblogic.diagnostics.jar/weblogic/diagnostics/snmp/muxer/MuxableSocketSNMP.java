package weblogic.diagnostics.snmp.muxer;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.diagnostics.snmp.agent.SNMPTransportProvider;
import weblogic.diagnostics.snmp.agent.SNMPV3Agent;
import weblogic.diagnostics.snmp.agent.SNMPV3AgentToolkit;
import weblogic.protocol.ServerChannel;
import weblogic.protocol.ServerChannelManager;
import weblogic.socket.AbstractMuxableSocket;
import weblogic.socket.SocketMuxer;
import weblogic.utils.io.Chunk;
import weblogic.utils.io.ChunkedInputStream;
import weblogic.work.WorkManager;

public final class MuxableSocketSNMP extends AbstractMuxableSocket {
   private static final DebugLogger DEBUG_LOGGER = DebugLogger.getDebugLogger("DebugSNMPProtocolTCP");
   static final int[] MSG_LENGTH_MULTIPLIERS = new int[]{1, 256, 65536, 16777216};
   private SNMPV3Agent snmpAgent;
   private WorkManager snmpWorkManager;

   MuxableSocketSNMP(Chunk headChunk, Socket s, ServerChannel networkChannel, SNMPV3Agent agent) throws IOException {
      super(headChunk, s, networkChannel);
      this.snmpAgent = agent;
      if (this.snmpAgent != null) {
         this.snmpWorkManager = this.snmpAgent.getSnmpWorkManagerInstance();
      }

      if (DEBUG_LOGGER.isDebugEnabled() && this.snmpWorkManager != null) {
         DEBUG_LOGGER.debug("Workmanager instance for SNMP traffic: " + this.snmpWorkManager.getName());
      }

   }

   MuxableSocketSNMP(InetAddress address, int port, ServerChannel channel) throws IOException {
      super(channel);
      this.connect(address, port);
   }

   public static MuxableSocketSNMP createConnection(String address, int port) throws IOException {
      ServerChannel channel = ServerChannelManager.findOutboundServerChannel(ProtocolHandlerSNMP.PROTOCOL_SNMP);
      MuxableSocketSNMP connection = new MuxableSocketSNMP(InetAddress.getByName(address), port, channel);
      SocketMuxer.getMuxer().register(connection);
      SocketMuxer.getMuxer().read(connection);
      return connection;
   }

   protected int getHeaderLength() {
      return 9;
   }

   protected int getMessageLength() {
      if (super.msgLength < 0) {
         int bufPos = 1;
         int headerLength = 2;
         int payloadLength = this.getHeaderByte(bufPos++);
         if ((payloadLength & 128) == 128) {
            int numLengthOctets = payloadLength & 127;
            headerLength += numLengthOctets;
            payloadLength = 0;

            for(int i = 0; i < numLengthOctets; ++i) {
               int maskValue = MSG_LENGTH_MULTIPLIERS[numLengthOctets - i - 1];
               byte nextLengthOctet = this.getHeaderByte(bufPos++);
               payloadLength += (nextLengthOctet & 255) * maskValue;
            }
         }

         super.msgLength = headerLength + payloadLength;
         if (DEBUG_LOGGER.isDebugEnabled()) {
            this.debug("getMessageLength", "msgLength == " + super.msgLength);
         }
      }

      return super.msgLength;
   }

   public void dispatch(Chunk chunks) {
      if (this.snmpWorkManager != null) {
         this.snmpWorkManager.schedule(new SnmpProtocolReader(chunks));
      }

   }

   public void hasException(Throwable t) {
      this.close();
   }

   public void endOfStream() {
      this.close();
   }

   public boolean timeout() {
      this.close();
      return true;
   }

   private void debug(String method, String msg) {
      Utils.debug(DEBUG_LOGGER, "MuxableSocketSNMP", method, msg);
   }

   private class SnmpProtocolReader implements Runnable {
      private Chunk dataChunks;

      public SnmpProtocolReader(Chunk chunks) {
         this.dataChunks = chunks;
      }

      public void run() {
         if (MuxableSocketSNMP.DEBUG_LOGGER.isDebugEnabled()) {
            this.debug("run", "processing message");
         }

         try {
            if (MuxableSocketSNMP.this.snmpAgent != null && MuxableSocketSNMP.this.snmpAgent.isSNMPAgentInitialized()) {
               SNMPV3AgentToolkit toolkit = (SNMPV3AgentToolkit)MuxableSocketSNMP.this.snmpAgent.getSNMPAgentToolkit();
               SNMPTransportProvider transportProvider = toolkit.getTransportProvider(1);
               if (transportProvider != null) {
                  ChunkedInputStream cis = new ChunkedInputStream(this.dataChunks, 0);
                  int msgSize = Chunk.size(this.dataChunks);
                  if (msgSize > 0) {
                     byte[] msg = new byte[msgSize];
                     int bytesAvail = cis.available();

                     for(int bytesRead = 0; bytesAvail > 0 && bytesRead < msgSize; bytesAvail = cis.available()) {
                        bytesRead += cis.read(msg, bytesRead, bytesAvail);
                     }

                     if (MuxableSocketSNMP.DEBUG_LOGGER.isDebugEnabled()) {
                        this.debug("run", "pushing message into agent");
                     }

                     transportProvider.pushMessage(MuxableSocketSNMP.this, msg);
                  }
               }
            }
         } catch (Throwable var8) {
            this.debug("run", "Caught exception: " + var8.getMessage());
            SocketMuxer.getMuxer().deliverHasException(MuxableSocketSNMP.this.getSocketFilter(), var8);
            MuxableSocketSNMP.this.close();
         }

      }

      private void debug(String method, String msg) {
         if (MuxableSocketSNMP.DEBUG_LOGGER.isDebugEnabled()) {
            Utils.debug(MuxableSocketSNMP.DEBUG_LOGGER, "MuxableSocketSNMP.SnmpProtocolReader:", method, msg);
         }

      }
   }
}
