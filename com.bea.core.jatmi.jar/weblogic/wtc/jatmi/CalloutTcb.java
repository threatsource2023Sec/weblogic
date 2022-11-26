package weblogic.wtc.jatmi;

import com.bea.core.jatmi.common.Utilities;
import com.bea.core.jatmi.common.ntrace;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public final class CalloutTcb extends tcb {
   private int co_version = 1;
   private ClientInfo co_src = new ClientInfo();
   private ClientInfo co_dest = new ClientInfo();
   private int co_port;
   private int co_appkey;
   private int co_conn_gen;
   private int co_conn_id;
   private String co_host;
   private int co_hostlen;
   private int co_flags;
   private int co_unused1;
   private int co_unused2;
   private int co_unused3;
   private int co_unused4;
   private int co_unused5;
   private int co_unused6;
   private transient byte[] myScratch;
   public static final int TMMSG_CALLOUT_VERSION = 1;

   public CalloutTcb() {
      super((short)16);
   }

   public int getVersion() {
      return this.co_version;
   }

   public ClientInfo getSrc() {
      return this.co_src;
   }

   public ClientInfo getDest() {
      return this.co_dest;
   }

   public int getPort() {
      return this.co_port;
   }

   public int getConnGen() {
      return this.co_conn_gen;
   }

   public int getConnId() {
      return this.co_conn_id;
   }

   public int getFlags() {
      return this.co_flags;
   }

   public int getHostLen() {
      return this.co_hostlen;
   }

   public String getHost() {
      return this.co_host;
   }

   public void setSrc(ClientInfo newSrc) {
      this.co_src = newSrc;
   }

   public void setDest(ClientInfo newDest) {
      this.co_dest = newDest;
   }

   public void setHost(String newHost) {
      this.co_host = newHost;
   }

   public void setVersion(int newVer) {
      this.co_version = newVer;
   }

   public void setPort(int newPort) {
      this.co_port = newPort;
   }

   public int getAppkey() {
      return this.co_appkey;
   }

   public void setAppkey(int newAppkey) {
      this.co_appkey = newAppkey;
   }

   public void setConnGen(int newConnGen) {
      this.co_conn_gen = newConnGen;
   }

   public void setConnId(int newConnId) {
      this.co_conn_id = newConnId;
   }

   public void setHostlen(int newHostlen) {
      this.co_hostlen = newHostlen;
   }

   public void setFlags(int newFlags) {
      this.co_flags = newFlags;
   }

   public boolean prepareForCache() {
      this.co_version = 1;
      this.co_src = new ClientInfo();
      this.co_dest = new ClientInfo();
      this.co_port = 0;
      this.co_appkey = 0;
      this.co_conn_gen = 0;
      this.co_conn_id = 0;
      this.co_host = null;
      this.co_hostlen = 0;
      this.co_flags = 0;
      return true;
   }

   private int roundup4(int a) {
      return a + 3 & -4;
   }

   public void _tmpresend(DataOutputStream encoder, tch myheader) throws TPException, IOException {
      boolean traceEnabled = ntrace.isTraceEnabled(4);
      if (traceEnabled) {
         ntrace.doTrace("[/CalloutTcb/_tmpresend/" + myheader);
      }

      int calculated_size = myheader.getHeaderLen();
      int tmmsg_clientinfo_sz = 44;
      calculated_size += 4 + tmmsg_clientinfo_sz + tmmsg_clientinfo_sz + 4 + 4 + 4 + 4 + 4 + 4 + 4 + 4 + 4 + 4 + 4 + 4 + Utilities.xdr_length_string(this.co_host);
      myheader.setLen(calculated_size);

      try {
         if (traceEnabled) {
            ntrace.doTrace("/CalloutTcb/_tmpresend/10");
         }

         encoder.writeInt(this.co_version);
         encoder.writeInt(this.co_src.getVersion());
         encoder.writeInt(this.co_src.getId().getTimestamp());
         encoder.writeInt(this.co_src.getId().getMchid());
         encoder.writeInt(this.co_src.getId().getSlot());
         encoder.writeInt(this.co_src.getPid());
         encoder.writeInt(this.co_src.getQaddr());
         encoder.writeInt(this.co_src.getCntxt());
         encoder.writeInt(this.co_src.getSSLPort());
         encoder.writeInt(this.co_src.getSSLSupports());
         encoder.writeInt(this.co_src.getSSLRequires());
         encoder.writeInt(0);
         encoder.writeInt(this.co_dest.getVersion());
         encoder.writeInt(this.co_dest.getId().getTimestamp());
         encoder.writeInt(this.co_dest.getId().getMchid());
         encoder.writeInt(this.co_dest.getId().getSlot());
         encoder.writeInt(this.co_dest.getPid());
         encoder.writeInt(this.co_dest.getQaddr());
         encoder.writeInt(this.co_dest.getCntxt());
         encoder.writeInt(this.co_dest.getSSLPort());
         encoder.writeInt(this.co_dest.getSSLSupports());
         encoder.writeInt(this.co_dest.getSSLRequires());
         encoder.writeInt(0);
         encoder.writeInt(this.co_port);
         encoder.writeInt(this.co_appkey);
         encoder.writeInt(this.co_conn_gen);
         encoder.writeInt(this.co_conn_id);
         encoder.writeInt(this.co_hostlen);
         encoder.writeInt(this.co_flags);
         encoder.writeInt(this.co_unused1);
         encoder.writeInt(this.co_unused2);
         encoder.writeInt(this.co_unused3);
         encoder.writeInt(this.co_unused4);
         encoder.writeInt(this.co_unused5);
         encoder.writeInt(this.co_unused6);
         Utilities.xdr_encode_string(encoder, this.co_host);
      } catch (IOException var8) {
         if (traceEnabled) {
            ntrace.doTrace("*]/CalloutTcb/_tmpresend/20" + var8);
         }

         throw var8;
      }

      if (traceEnabled) {
         ntrace.doTrace("]/CalloutTcb/_tmpresend/30");
      }

   }

   public final int _tmpostrecv(DataInputStream decoder, int recv_size, int hint_index) throws IOException {
      if (this.myScratch == null) {
         this.myScratch = new byte[150];
      }

      this.co_version = decoder.readInt();
      this.co_src.setVersion(decoder.readInt());
      this.co_src.getId().setTimestamp(decoder.readInt());
      this.co_src.getId().setMchid(decoder.readInt());
      this.co_src.getId().setSlot(decoder.readInt());
      this.co_src.setPid(decoder.readInt());
      this.co_src.setQaddr(decoder.readInt());
      this.co_src.setCntxt(decoder.readInt());
      this.co_src.setSSLPort((short)decoder.readInt());
      this.co_src.setSSLSupports((short)decoder.readInt());
      this.co_src.setSSLRequires((short)decoder.readInt());
      decoder.readInt();
      this.co_dest.setVersion(decoder.readInt());
      this.co_dest.getId().setTimestamp(decoder.readInt());
      this.co_dest.getId().setMchid(decoder.readInt());
      this.co_dest.getId().setSlot(decoder.readInt());
      this.co_dest.setPid(decoder.readInt());
      this.co_dest.setQaddr(decoder.readInt());
      this.co_dest.setCntxt(decoder.readInt());
      this.co_dest.setSSLPort((short)decoder.readInt());
      this.co_dest.setSSLSupports((short)decoder.readInt());
      this.co_dest.setSSLRequires((short)decoder.readInt());
      decoder.readInt();
      this.co_port = decoder.readInt();
      this.co_appkey = decoder.readInt();
      this.co_conn_gen = decoder.readInt();
      this.co_conn_id = decoder.readInt();
      this.co_hostlen = decoder.readInt();
      this.co_flags = decoder.readInt();
      decoder.readInt();
      decoder.readInt();
      decoder.readInt();
      decoder.readInt();
      decoder.readInt();
      decoder.readInt();
      this.co_host = Utilities.xdr_decode_string(decoder, this.myScratch);
      return 0;
   }
}
