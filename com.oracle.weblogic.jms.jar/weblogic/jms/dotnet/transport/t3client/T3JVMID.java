package weblogic.jms.dotnet.transport.t3client;

final class T3JVMID {
   private static final long serialVersionUID = -2573312136796037590L;
   static final int INVALID_PORT = -1;
   private static final long INVALID_DIFFERENTIATOR = 0L;
   static final byte HAS_HOST_ADDRESS = 1;
   private static final byte HAS_ROUTER = 2;
   private static final byte HAS_CLUSTER_ADDRESS = 4;
   static final byte HAS_DOMAIN_NAME = 8;
   static final byte HAS_SERVER_NAME = 16;
   static final byte HAS_DNS_NAME = 32;
   private static final byte HAS_CHANNEL = 64;
   private static final byte SC_BLOCK_DATA = 8;
   private static final byte SC_EXTERNALIZABLE = 4;
   private static final byte TC_NULL = 112;
   private static final byte TC_CLASSDESC = 114;
   private static final byte TC_OBJECT = 115;
   private static final byte TC_BLOCKDATA = 119;
   private static final byte TC_ENDBLOCKDATA = 120;
   private static final short STREAM_MAGIC = -21267;
   private static final short STREAM_VERSION = 5;
   private static final String className = "weblogic.rjvm.JVMID";
   private byte flags;
   private String hostAddress;
   private String clusterAddress;
   private byte arrayLength;
   private long differentiator;
   private int rawAddress;
   private int[] ports;
   private String domainName;
   private String serverName;
   private String dnsName;
   private byte[] buf;

   T3JVMID(byte flags, long differentiator, String hostAddress, int rawAddress, int[] ports) {
      this.flags = flags;
      this.differentiator = differentiator;
      this.hostAddress = hostAddress;
      this.rawAddress = rawAddress;
      if (ports != null) {
         this.ports = new int[ports.length];

         for(int i = 0; i < ports.length; ++i) {
            this.ports[i] = ports[i];
         }
      }

      MarshalWriterImpl mwi = new MarshalWriterImpl();
      mwi.write(0);
      this.write(mwi);
      this.buf = mwi.toByteArray();
   }

   byte getFlags() {
      return this.flags;
   }

   byte[] getBuf() {
      return this.buf;
   }

   long getDifferentiator() {
      return this.differentiator;
   }

   String getHostAddress() {
      return this.hostAddress;
   }

   int getRawAddress() {
      return this.rawAddress;
   }

   private void write2(MarshalWriterImpl out) {
      out.write(this.buf, 0, this.buf.length);
   }

   private void write(MarshalWriterImpl out) {
      out.writeShort((short)-21267);
      out.writeShort((short)5);
      out.write(115);
      out.write(114);
      out.writeUTF("weblogic.rjvm.JVMID");
      out.writeLong(-2573312136796037590L);
      out.write(12);
      out.writeShort((int)0);
      out.write(120);
      out.write(112);
      out.write(119);
      int startPos = out.getPosition();
      out.skip(1);
      out.write(this.flags);
      out.writeLong(this.differentiator);
      out.writeUTF(this.hostAddress);
      if (this.dnsName != null) {
         out.writeUTF(this.dnsName);
      }

      out.writeInt(this.rawAddress);
      int i;
      if (this.ports != null && this.ports.length != 0) {
         out.writeInt(this.ports.length);

         for(i = 0; i < this.ports.length; ++i) {
            out.writeInt(this.ports[i]);
         }
      } else {
         out.writeInt(0);
      }

      i = out.getPosition();
      int len = i - startPos - 1;
      out.setPosition(startPos);
      out.write(len);
      out.setPosition(i);
      out.write(120);
   }
}
