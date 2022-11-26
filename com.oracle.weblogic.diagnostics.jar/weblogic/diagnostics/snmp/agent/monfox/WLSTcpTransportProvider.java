package weblogic.diagnostics.snmp.agent.monfox;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;
import monfox.toolkit.snmp.engine.SnmpBuffer;
import monfox.toolkit.snmp.engine.SnmpTransportException;
import monfox.toolkit.snmp.engine.TcpEntity;
import monfox.toolkit.snmp.engine.TransportEntity;
import monfox.toolkit.snmp.engine.TransportProvider;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.diagnostics.snmp.agent.SNMPTransportProvider;
import weblogic.diagnostics.snmp.i18n.SNMPLogger;
import weblogic.diagnostics.snmp.muxer.MuxableSocketSNMP;
import weblogic.socket.MuxableSocket;

public class WLSTcpTransportProvider extends TransportProvider implements SNMPTransportProvider {
   private boolean isActive = true;
   private static final DebugLogger DEBUG_LOGGER = DebugLogger.getDebugLogger("DebugSNMPToolkit");

   public boolean isPushProvider() {
      return true;
   }

   public void initialize(TransportProvider.Params params) throws SnmpTransportException {
      super.initialize(params);
   }

   public int getTransportType() {
      return 2;
   }

   public void initialize(InetAddress local_address, int local_port) throws SnmpTransportException {
      this.isActive = true;
   }

   public boolean isActive() {
      return this.isActive;
   }

   public void shutdown() throws SnmpTransportException {
      this.isActive = false;
   }

   public Object send(Object data, TransportEntity target) throws SnmpTransportException {
      if (!(target instanceof WLSTcpEntity)) {
         throw new SnmpTransportException("wrong transport provider type:" + target);
      } else {
         WLSTcpEntity entity = (WLSTcpEntity)target;

         try {
            MuxableSocket socket = entity.getDestinationSocket();
            if (socket == null) {
               if (DEBUG_LOGGER.isDebugEnabled()) {
                  DEBUG_LOGGER.debug("Creating outbound SNMP TCP connection to host " + entity.getAddress().getHostAddress() + " on port " + entity.getPort());
               }

               socket = MuxableSocketSNMP.createConnection(entity.getAddress().getHostAddress(), entity.getPort());
            }

            if (!(data instanceof SnmpBuffer)) {
               throw new SnmpTransportException("unknown data class:" + data.getClass().getName());
            }

            SnmpBuffer buf = (SnmpBuffer)data;
            if (DEBUG_LOGGER.isDebugEnabled()) {
               DEBUG_LOGGER.debug("Writing outgoing SNMP message, length " + buf.length);
            }

            OutputStream os = ((MuxableSocket)socket).getSocket().getOutputStream();
            os.write(buf.data, buf.offset, buf.length);
         } catch (IOException var7) {
            SNMPLogger.logTCPProviderSendException(var7);
         }

         return data;
      }
   }

   public TransportEntity receive(SnmpBuffer buffer, boolean use_entity) throws SnmpTransportException {
      return null;
   }

   public static void log(String msg) {
      DebugLogger.println((new Date(System.currentTimeMillis())).toString() + ": " + msg);
   }

   public void pushMessage(MuxableSocket socket, byte[] msgBytes) {
      SnmpBuffer snmpBuf = new SnmpBuffer(msgBytes);
      WLSTcpEntity entity = new WLSTcpEntity();
      entity.setProvider(this);
      entity.setDestinationSocket(socket);
      super.pushMessage(entity, snmpBuf);
   }

   public int getType() {
      return 1;
   }

   private class WLSTcpEntity extends TcpEntity {
      private MuxableSocket destinationSocket;

      public WLSTcpEntity() {
      }

      public WLSTcpEntity(MuxableSocket s) {
         this.destinationSocket = s;
      }

      public MuxableSocket getDestinationSocket() {
         return this.destinationSocket;
      }

      public void setDestinationSocket(MuxableSocket destinationSocket) {
         this.destinationSocket = destinationSocket;
      }
   }

   public static class Params extends TransportProvider.Params {
      public Params() {
      }

      public Params(String address, int port) throws UnknownHostException {
         super(address == null ? null : InetAddress.getByName(address), port);
      }

      public Params(InetAddress address, int port) {
         super(address, port);
      }

      public int getTransportType() {
         return 2;
      }
   }
}
