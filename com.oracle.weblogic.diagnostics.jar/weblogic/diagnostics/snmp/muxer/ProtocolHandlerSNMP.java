package weblogic.diagnostics.snmp.muxer;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.net.Socket;
import java.security.AccessController;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.diagnostics.snmp.agent.SNMPV3Agent;
import weblogic.diagnostics.snmp.i18n.SNMPLogger;
import weblogic.management.configuration.NetworkAccessPointMBean;
import weblogic.management.configuration.ServerMBean;
import weblogic.management.provider.ManagementService;
import weblogic.management.provider.RuntimeAccess;
import weblogic.protocol.Protocol;
import weblogic.protocol.ProtocolHandler;
import weblogic.protocol.ProtocolManager;
import weblogic.protocol.ServerChannel;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.server.channels.ServerChannelImpl;
import weblogic.socket.MuxableSocket;
import weblogic.utils.ArrayUtils;
import weblogic.utils.io.Chunk;

public class ProtocolHandlerSNMP implements ProtocolHandler, PropertyChangeListener, ArrayUtils.DiffHandler {
   private static final AuthenticatedSubject KERNEL_ID = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private static final String PROTOCOL_NAME = "snmp";
   static final byte ASN1_SEQUENCE_DEFINITE_LENGTH = 48;
   static final byte BER_ENCODED_INTEGER = 2;
   static final int MULTIBYTE_LENGTH_TAG = 128;
   static final int MULTIBYTE_LENGTH_MASK = 127;
   private static final int SNMP_V1_TAG = 0;
   private static final int SNMP_V2_TAG = 1;
   private static final int SNMP_V3_TAG = 3;
   private static final DebugLogger DEBUG = DebugLogger.getDebugLogger("DebugSNMPProtocolTCP");
   static final int DISCRIMINATION_LENGTH = 9;
   private static final ProtocolHandlerSNMP theOne = new ProtocolHandlerSNMP();
   public static final Protocol PROTOCOL_SNMP = ProtocolManager.createProtocol((byte)14, "snmp", "snmp", false, getProtocolHandler());
   protected SNMPV3Agent snmpAgent;
   private boolean usingDedicatedSnmpChannel;
   private static int SNMP_PRIORITY = 2147483646;
   private ServerMBean server = this.getServerRuntimeConfiguration();

   public static ProtocolHandler getProtocolHandler() {
      if (DEBUG.isDebugEnabled()) {
         Utils.debug(DEBUG, "ProtocolHandlerSNMP", "getSNMPProtocolHandler", "");
      }

      return getSNMPProtocolHandler();
   }

   public static ProtocolHandlerSNMP getSNMPProtocolHandler() {
      if (DEBUG.isDebugEnabled()) {
         Utils.debug(DEBUG, "ProtocolHandlerSNMP", "getSNMPProtocolHandler", "");
      }

      return theOne;
   }

   private ProtocolHandlerSNMP() {
      this.server.addPropertyChangeListener(this);
      if (DEBUG.isDebugEnabled()) {
         DEBUG.debug("Initializing, checking for dedicated SNMP channel.");
      }

      this.usingDedicatedSnmpChannel = this.checkForDedicatedSNMPChannel(this.server.getNetworkAccessPoints());
      NetworkAccessPointMBean[] napList = this.server.getNetworkAccessPoints();
      if (napList != null) {
         for(int i = 0; i < napList.length; ++i) {
            if (napList[i] != null) {
               napList[i].addPropertyChangeListener(this);
            }
         }
      }

   }

   private ServerMBean getServerRuntimeConfiguration() {
      RuntimeAccess rt = ManagementService.getRuntimeAccess(KERNEL_ID);
      ServerMBean server = rt.getServer();
      return server;
   }

   public ServerChannel getDefaultServerChannel() {
      return ProtocolHandlerSNMP.ChannelInitializer.DEFAULT_SNMP_CHANNEL;
   }

   public int getHeaderLength() {
      return 9;
   }

   public int getPriority() {
      return SNMP_PRIORITY;
   }

   public Protocol getProtocol() {
      return PROTOCOL_SNMP;
   }

   public boolean claimSocket(Chunk head) {
      if (this.snmpAgent != null && this.snmpAgent.isSNMPAgentInitialized()) {
         if (head.end < 9) {
            return false;
         } else {
            byte[] buf = head.buf;
            int pos = 0;
            if (buf[pos++] != 48) {
               return false;
            } else {
               int msgLength = buf[pos++];
               if ((msgLength & 128) == 128) {
                  int numLengthOctets = msgLength & 127;
                  if (numLengthOctets > MuxableSocketSNMP.MSG_LENGTH_MULTIPLIERS.length) {
                     if (DEBUG.isDebugEnabled()) {
                        this.debug("claimSocket", "multi-byte length field greater than 4");
                     }

                     return false;
                  }

                  pos += numLengthOctets;
               }

               if (buf[pos++] != 2) {
                  return false;
               } else if ((buf[pos] & 128) == 128) {
                  return false;
               } else {
                  int versionFieldLen = buf[pos++];
                  if (versionFieldLen > 1) {
                     return false;
                  } else {
                     int snmpVersion = buf[pos++];
                     switch (snmpVersion) {
                        case 0:
                        case 1:
                        case 3:
                           if (DEBUG.isDebugEnabled()) {
                              this.debug("claimSocket", "Valid SNMP packet, version: " + snmpVersion);
                           }

                           return true;
                        case 2:
                        default:
                           if (DEBUG.isDebugEnabled()) {
                              this.debug("claimSocket", "Invalid SNMP packet version: " + snmpVersion);
                           }

                           return false;
                     }
                  }
               }
            }
         }
      } else {
         return false;
      }
   }

   public MuxableSocket createSocket(Chunk head, Socket s, ServerChannel networkChannel) throws IOException {
      if (this.usingDedicatedSnmpChannel && networkChannel.getChannelName().startsWith("Default")) {
         String msg = SNMPLogger.logUseOfDefaultSnmpChannelDetectedLoggable().getMessage();
         throw new InvalidSNMPNetworkChannelException(msg);
      } else {
         MuxableSocketSNMP ms = new MuxableSocketSNMP(head, s, networkChannel, this.snmpAgent);
         return ms;
      }
   }

   public void propertyChange(PropertyChangeEvent evt) {
      Object source = evt.getSource();
      if (source instanceof ServerMBean) {
         if (evt.getPropertyName().equals("NetworkAccessPoints")) {
            if (DEBUG.isDebugEnabled()) {
               DEBUG.debug("NetworkAccessPoints changed, processing changes");
            }

            NetworkAccessPointMBean[] oldValues = (NetworkAccessPointMBean[])((NetworkAccessPointMBean[])evt.getOldValue());
            NetworkAccessPointMBean[] newValues = (NetworkAccessPointMBean[])((NetworkAccessPointMBean[])evt.getNewValue());
            ArrayUtils.computeDiff(oldValues, newValues, this);
            this.usingDedicatedSnmpChannel = this.checkForDedicatedSNMPChannel(newValues);
         }
      } else if (source instanceof NetworkAccessPointMBean) {
         if (DEBUG.isDebugEnabled()) {
            DEBUG.debug("NetworkAccessPoints changed, processing changes");
         }

         if (evt.getPropertyName().equals("Enabled")) {
            this.usingDedicatedSnmpChannel = this.checkForDedicatedSNMPChannel(this.server.getNetworkAccessPoints());
         }
      }

   }

   private boolean checkForDedicatedSNMPChannel(NetworkAccessPointMBean[] newValues) {
      if (newValues != null) {
         for(int i = 0; i < newValues.length; ++i) {
            NetworkAccessPointMBean nap = newValues[i];
            if (nap != null && nap.isEnabled() && nap.getProtocol().equals("snmp") && !nap.getName().startsWith("Default")) {
               if (DEBUG.isDebugEnabled()) {
                  DEBUG.debug("Found dedicated SNMP channel, channel name: " + nap.getName());
               }

               return true;
            }
         }
      }

      if (DEBUG.isDebugEnabled()) {
         DEBUG.debug("Using default channel for SNMP traffic.");
      }

      return false;
   }

   public void setAgent(SNMPV3Agent agent) {
      this.snmpAgent = agent;
   }

   public SNMPV3Agent getAgent() {
      return this.snmpAgent;
   }

   private void debug(String method, String msg) {
      Utils.debug(DEBUG, "ProtocolHandlerSNMP", method, msg);
   }

   public void addObject(Object added) {
      if (added instanceof NetworkAccessPointMBean) {
         NetworkAccessPointMBean nap = (NetworkAccessPointMBean)added;
         if (nap.getProtocol().equals("snmp")) {
            nap.addPropertyChangeListener(this);
            if (DEBUG.isDebugEnabled()) {
               DEBUG.debug("Registering protocol handler for property change notifications with NAP named " + nap.getName());
            }
         }
      }

   }

   public void removeObject(Object removed) {
      if (removed instanceof NetworkAccessPointMBean) {
         NetworkAccessPointMBean nap = (NetworkAccessPointMBean)removed;
         if (nap.getProtocol().equals("snmp")) {
            if (DEBUG.isDebugEnabled()) {
               DEBUG.debug("NAP " + nap.getName() + " removed, unregistering for property change notifications");
            }

            nap.removePropertyChangeListener(this);
         }
      }

   }

   private static final class ChannelInitializer {
      private static final ServerChannel DEFAULT_SNMP_CHANNEL;

      static {
         DEFAULT_SNMP_CHANNEL = ServerChannelImpl.createDefaultServerChannel(ProtocolHandlerSNMP.PROTOCOL_SNMP);
      }
   }
}
