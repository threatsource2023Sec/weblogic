package weblogic.wtc.jatmi;

import com.bea.core.jatmi.common.Utilities;
import com.bea.core.jatmi.common.ntrace;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public final class RouteTcb extends tcb {
   private short ro_version = 2;
   private short ro_port;
   private int ro_aomhandle = -1;
   private short ro_svc_tmid_ind = 1;
   private short ro_ntmids;
   private short ro_flags;
   private String host;
   private int hostLen;
   private int[] svcTmid;
   private ClientInfo recvSrcCltinfo = new ClientInfo();
   private Objinfo objinfo;
   public static final int TMMSG_CLIENTINFO_VERSION = 1;
   public static final int TMCLIENTINFO_VERSION = 1;
   public static final int TMMSG_ROUTE_VERSION = 2;
   private transient byte[] myScratch;

   public RouteTcb(int hostlen) {
      super((short)9);
   }

   public int getNTmids() {
      return this.ro_ntmids;
   }

   public int getAOMHandle() {
      return this.ro_aomhandle;
   }

   public int getVersion() {
      return this.ro_version;
   }

   public ClientInfo getRecvSrcCltinfo() {
      return this.recvSrcCltinfo;
   }

   public void setRecvSrcCltinfo(ClientInfo newRecvSrcCltinfo) {
      this.recvSrcCltinfo = newRecvSrcCltinfo;
   }

   public void setHost(String newHost) {
      this.host = newHost;
   }

   public int getHostLen() {
      return this.hostLen;
   }

   public String getHost() {
      return this.host;
   }

   public int[] getSvcTmid() {
      return this.svcTmid;
   }

   public short getFlags() {
      return this.ro_flags;
   }

   public void setFlags(short newFlags) {
      this.ro_flags = newFlags;
   }

   public void setSvcTmidInd(short newSvcTmidInd) {
      this.ro_svc_tmid_ind = newSvcTmidInd;
   }

   public void setPort(short newPort) {
      this.ro_port = newPort;
   }

   public short getPort() {
      return this.ro_port;
   }

   public void setHostLen(int newHostLen) {
      this.hostLen = newHostLen;
   }

   public void setObjinfo(Objinfo newObjinfo) {
      this.objinfo = newObjinfo;
   }

   public boolean prepareForCache() {
      this.ro_version = 0;
      this.ro_port = 0;
      this.ro_aomhandle = 0;
      this.ro_svc_tmid_ind = 0;
      this.ro_ntmids = 0;
      this.ro_flags = 0;
      this.host = null;
      this.hostLen = 0;
      this.svcTmid = null;
      this.recvSrcCltinfo = new ClientInfo();
      this.objinfo = null;
      return true;
   }

   private int roundup4(int a) {
      return a + 3 & -4;
   }

   public void _tmpresend(DataOutputStream encoder, tch myheader) throws TPException, IOException {
      boolean traceEnabled = ntrace.isTraceEnabled(4);
      if (traceEnabled) {
         ntrace.doTrace("[/RouteTcb/_tmpresend/" + myheader);
      }

      int calculated_size = myheader.getHeaderLen();
      calculated_size += 24 + Utilities.xdr_length_string(this.host);
      if (this.ro_ntmids > 0) {
         calculated_size += 4 * this.ro_ntmids;
      }

      if (this.ro_version > 1) {
         calculated_size += 44;
      }

      myheader.setLen(calculated_size);
      if (this.objinfo.getSendSrcCltinfo() != null) {
         this.recvSrcCltinfo.setVersion(1);
         this.recvSrcCltinfo.getId().setTimestamp(this.objinfo.getSendSrcCltinfo().getId().getTimestamp());
         this.recvSrcCltinfo.getId().setMchid(this.objinfo.getSendSrcCltinfo().getId().getMchid());
         this.recvSrcCltinfo.getId().setSlot(this.objinfo.getSendSrcCltinfo().getId().getSlot());
         this.recvSrcCltinfo.setPid(this.objinfo.getSendSrcCltinfo().getPid());
         this.recvSrcCltinfo.setQaddr(this.objinfo.getSendSrcCltinfo().getQaddr());
         this.recvSrcCltinfo.setCntxt(this.objinfo.getSendSrcCltinfo().getCntxt());
         this.recvSrcCltinfo.setSSLPort(this.objinfo.getSendSrcCltinfo().getSSLPort());
         this.recvSrcCltinfo.setSSLSupports(this.objinfo.getSendSrcCltinfo().getSSLSupports());
         this.recvSrcCltinfo.setSSLRequires(this.objinfo.getSendSrcCltinfo().getSSLRequires());
      }

      try {
         if (traceEnabled) {
            ntrace.doTrace("/RouteTcb/_tmpresend/10");
         }

         encoder.writeInt(this.ro_version);
         encoder.writeInt(this.ro_port);
         encoder.writeInt(this.ro_aomhandle);
         encoder.writeInt(this.ro_svc_tmid_ind);
         encoder.writeInt(this.ro_ntmids);
         encoder.writeInt(this.ro_flags);
         Utilities.xdr_encode_string(encoder, this.host);
         if (this.ro_ntmids > 0) {
            for(int lcv = 0; lcv < this.ro_ntmids; ++lcv) {
               encoder.writeInt(this.svcTmid[lcv]);
            }
         }

         if (this.ro_version > 1) {
            encoder.writeInt(this.recvSrcCltinfo.getVersion());
            encoder.writeInt(this.recvSrcCltinfo.getId().getTimestamp());
            encoder.writeInt(this.recvSrcCltinfo.getId().getMchid());
            encoder.writeInt(this.recvSrcCltinfo.getId().getSlot());
            encoder.writeInt(this.recvSrcCltinfo.getPid());
            encoder.writeInt(this.recvSrcCltinfo.getQaddr());
            encoder.writeInt(this.recvSrcCltinfo.getCntxt());
            encoder.writeInt(this.recvSrcCltinfo.getSSLPort());
            encoder.writeInt(this.recvSrcCltinfo.getSSLSupports());
            encoder.writeInt(this.recvSrcCltinfo.getSSLRequires());
            encoder.writeInt(0);
         }
      } catch (IOException var7) {
         if (traceEnabled) {
            ntrace.doTrace("*]/RouteTcb/_tmpresend/20" + var7);
         }

         throw var7;
      }

      if (traceEnabled) {
         ntrace.doTrace("]/RouteTcb/_tmpresend/30");
      }

   }

   public final int _tmpostrecv(DataInputStream decoder, int recv_size, int hint_index) throws IOException {
      if (this.myScratch == null) {
         this.myScratch = new byte[150];
      }

      this.ro_version = (short)decoder.readInt();
      this.ro_port = (short)decoder.readInt();
      this.ro_aomhandle = decoder.readInt();
      this.ro_svc_tmid_ind = (short)decoder.readInt();
      this.ro_ntmids = (short)decoder.readInt();
      this.ro_flags = (short)decoder.readInt();
      this.host = Utilities.xdr_decode_string(decoder, this.myScratch);
      this.hostLen = this.host == null ? 0 : this.host.length();
      if (this.ro_ntmids > 0) {
         this.svcTmid = new int[this.ro_ntmids];

         for(int lcv = 0; lcv < this.ro_ntmids; ++lcv) {
            this.svcTmid[lcv] = decoder.readInt();
         }
      }

      if (this.ro_version > 1) {
         this.recvSrcCltinfo.setVersion(decoder.readInt());
         this.recvSrcCltinfo.getId().setTimestamp(decoder.readInt());
         this.recvSrcCltinfo.getId().setMchid(decoder.readInt());
         this.recvSrcCltinfo.getId().setSlot(decoder.readInt());
         this.recvSrcCltinfo.setPid(decoder.readInt());
         this.recvSrcCltinfo.setQaddr(decoder.readInt());
         this.recvSrcCltinfo.setCntxt(decoder.readInt());
         this.recvSrcCltinfo.setSSLPort((short)decoder.readInt());
         this.recvSrcCltinfo.setSSLSupports((short)decoder.readInt());
         this.recvSrcCltinfo.setSSLRequires((short)decoder.readInt());
         decoder.readInt();
      }

      return 0;
   }
}
