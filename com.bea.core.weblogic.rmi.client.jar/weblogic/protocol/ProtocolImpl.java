package weblogic.protocol;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.net.Socket;
import weblogic.socket.MuxableSocket;
import weblogic.utils.io.Chunk;

public final class ProtocolImpl implements Protocol, Externalizable {
   private static final long serialVersionUID = 6784177831072346070L;
   public static final byte UNKNOWN = 9;
   public static final byte NUM_PROTOCOLS = 9;
   public static final int PROTOCOL_T3_FLAG = 1;
   public static final int PROTOCOL_T3S_FLAG = 4;
   public static final String PROTOCOL_T3_NAME = "T3";
   public static final String PROTOCOL_T3S_NAME = "T3S";
   public static final String PROTOCOL_HTTPS_NAME = "HTTPS";
   public static final Protocol PROTOCOL_UNKNOWN = ProtocolManager.createProtocol((byte)9, "unknown", "unknown", false, new NullProtocolHandler());
   private static byte dynamicPn = 16;
   private String name;
   private String urlPrefix;
   private boolean secure;
   private byte protocolNumber;
   private ProtocolHandler handler;

   public ProtocolImpl() {
   }

   ProtocolImpl(byte pn, String name, String urlPrefix, boolean secure, ProtocolHandler handler) {
      this.protocolNumber = pn;
      this.secure = secure;
      this.name = name;
      this.urlPrefix = urlPrefix;
      this.handler = handler;
      ProtocolManager.registerProtocol(this);
   }

   ProtocolImpl(String name, String urlPrefix, boolean secure, ProtocolHandler handler) {
      this(getNextProtocol(), name, urlPrefix, secure, handler);
   }

   private static synchronized byte getNextProtocol() {
      byte var10000 = dynamicPn;
      dynamicPn = (byte)(var10000 + 1);
      return var10000;
   }

   public ProtocolHandler getHandler() {
      return this.handler;
   }

   public String getProtocolName() {
      return this.name;
   }

   public String getAsURLPrefix() {
      return this.urlPrefix;
   }

   public byte toByte() {
      return this.protocolNumber;
   }

   public byte getQOS() {
      if (this.protocolNumber == 6) {
         return 103;
      } else {
         return (byte)(this.isSecure() ? 102 : 101);
      }
   }

   public boolean isSatisfactoryQOS(byte QOS) {
      switch (QOS) {
         case 101:
            return !this.isUnknown();
         case 102:
            if (this.protocolNumber == 6) {
               return false;
            }

            return this.isSecure();
         case 103:
            return this.protocolNumber == 6;
         default:
            throw new IllegalArgumentException("Unknown QOS: '" + QOS + "'");
      }
   }

   public boolean isSecure() {
      return this.protocolNumber == 6 ? ProtocolManager.getDefaultAdminProtocol().isSecure() : this.secure;
   }

   public boolean isUnknown() {
      return this.protocolNumber == 9;
   }

   public boolean isEnabled() {
      return !this.isUnknown();
   }

   public Protocol upgrade() {
      return (Protocol)(this.isSecure() ? this : ProtocolManager.getProtocolByName(this.getProtocolName() + "S"));
   }

   public int hashCode() {
      return this.protocolNumber;
   }

   public boolean equals(Object o) {
      if (o == null) {
         return false;
      } else if (o == this) {
         return true;
      } else {
         try {
            return ((ProtocolImpl)o).protocolNumber == this.protocolNumber;
         } catch (ClassCastException var3) {
            return false;
         }
      }
   }

   public String toString() {
      return this.getProtocolName();
   }

   public void writeExternal(ObjectOutput out) throws IOException {
      out.writeUTF(this.name);
      out.writeUTF(this.urlPrefix);
      out.writeBoolean(this.secure);
      out.writeByte(this.protocolNumber);
   }

   public void readExternal(ObjectInput in) throws IOException {
      this.name = in.readUTF();
      this.urlPrefix = in.readUTF();
      this.secure = in.readBoolean();
      this.protocolNumber = in.readByte();
   }

   public Object readResolve() {
      Protocol p = ProtocolManager.getProtocolByIndex(this.protocolNumber);
      return p.toByte() != 9 ? p : this;
   }

   static class NullProtocolHandler implements ProtocolHandler {
      public ServerChannel getDefaultServerChannel() {
         return null;
      }

      public int getHeaderLength() {
         return 0;
      }

      public int getPriority() {
         return 0;
      }

      public boolean claimSocket(Chunk head) {
         return false;
      }

      public MuxableSocket createSocket(Chunk head, Socket s, ServerChannel networkChannel) throws IOException {
         return null;
      }

      public Protocol getProtocol() {
         return null;
      }
   }
}
