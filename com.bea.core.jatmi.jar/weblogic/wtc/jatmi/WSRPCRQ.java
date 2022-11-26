package weblogic.wtc.jatmi;

import com.bea.core.jatmi.common.Utilities;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import weblogic.wtc.WTCLogger;

public final class WSRPCRQ extends StandardTypes implements TypedBuffer {
   private int type;
   public InetAddress wsh_addr;
   public int port;
   public int timestamp;
   private int hdlr_pid;
   public int options;
   public short notify;
   private short usignal;
   public short auth_type;
   int wsinitrp_notifyopt;
   int wsinitrp_mchidshift;
   int wsinitrp_flags;
   int wsinitrp_nettimeout;
   private String wsinitrq_usrname;
   private String wsinitrq_cltname;
   private int wsinitrq_notifyopt;
   private static final int WSMAXNETADDR = 64;
   private static final int WSMINNETADDR = 8;
   static final int TPU_SIG = 1;
   static final int TPU_DIP = 2;
   static final int TPU_IGN = 4;
   static final int TPU_THREAD = 64;
   static final int TPU_MASK = 71;
   static final int TMCMTLOGGED = 32;
   static final int TMSHM = 67108864;
   private static final int WSINIT_RQ_VERSION = 1;
   private static final int WSINIT_RP_VERSION = 1;

   public WSRPCRQ() {
      super("wsrpcrq", 11);
      this.type = 0;
   }

   public WSRPCRQ(String usrname, String cltname) {
      super("wsrpcrq", 11);
      this.type = 1;
      if (usrname != null) {
         this.wsinitrq_usrname = new String(usrname);
      }

      if (cltname != null) {
         this.wsinitrq_cltname = new String(cltname);
      }

      this.wsinitrq_notifyopt = 64;
   }

   public void setINIT() {
      this.type = 1;
   }

   private int roundup4(int a) {
      return a + 3 & -4;
   }

   public int get_notifyopt() {
      return this.wsinitrp_notifyopt;
   }

   public void _tmpresend(DataOutputStream encoder) throws TPException, IOException {
      int output_data_size = false;
      if (this.type != 1) {
         throw new TPException(9, "Workstation client never sends WSRPCRQ");
      } else {
         encoder.writeInt(1);
         int pad_bytes;
         int lcv;
         byte[] wsinitrq_cltnameBytes;
         if (this.wsinitrq_usrname == null) {
            encoder.writeInt(0);
         } else {
            wsinitrq_cltnameBytes = Utilities.getEncBytes(this.wsinitrq_usrname);
            pad_bytes = this.roundup4(wsinitrq_cltnameBytes.length) - wsinitrq_cltnameBytes.length;
            encoder.writeInt(wsinitrq_cltnameBytes.length);
            encoder.write(wsinitrq_cltnameBytes);

            for(lcv = 0; lcv < pad_bytes; ++lcv) {
               encoder.writeByte(0);
            }
         }

         if (this.wsinitrq_cltname == null) {
            encoder.writeInt(0);
         } else {
            wsinitrq_cltnameBytes = Utilities.getEncBytes(this.wsinitrq_cltname);
            pad_bytes = this.roundup4(wsinitrq_cltnameBytes.length) - wsinitrq_cltnameBytes.length;
            encoder.writeInt(wsinitrq_cltnameBytes.length);
            encoder.write(wsinitrq_cltnameBytes);

            for(lcv = 0; lcv < pad_bytes; ++lcv) {
               encoder.writeByte(0);
            }
         }

         encoder.writeInt(this.wsinitrq_notifyopt);
      }
   }

   private void postrecv_wsrpcrq(DataInputStream decoder, int recv_size) throws TPException, IOException {
      decoder.skipBytes(64);
      recv_size -= 64;
      int handler_addr_len;
      if ((handler_addr_len = decoder.readInt()) != 8) {
         WTCLogger.logInvHandlerAddrLength(handler_addr_len);
         throw new TPException(4, "Invalid handler address length " + handler_addr_len);
      } else {
         recv_size -= 4;
         decoder.skipBytes(2);
         recv_size -= 2;
         this.port = decoder.readUnsignedShort();
         recv_size -= 2;
         String wshstring = new String(decoder.readUnsignedByte() + "." + decoder.readUnsignedByte() + "." + decoder.readUnsignedByte() + "." + decoder.readUnsignedByte());
         this.wsh_addr = InetAddress.getByName(wshstring);
         recv_size -= 4;
         decoder.skipBytes(64 - handler_addr_len);
         recv_size -= 64 - handler_addr_len;
         this.timestamp = decoder.readInt();
         recv_size -= 4;
         this.hdlr_pid = decoder.readInt();
         recv_size -= 4;
         this.options = decoder.readInt();
         recv_size -= 4;
         this.notify = (short)decoder.readInt();
         recv_size -= 4;
         this.usignal = (short)decoder.readInt();
         recv_size -= 4;
         this.auth_type = (short)decoder.readInt();
         recv_size -= 4;
         decoder.skipBytes(recv_size);
      }
   }

   private void postrecv_wsinit_rp(DataInputStream decoder, int recv_size) throws IOException {
      this.wsinitrp_notifyopt = decoder.readInt();
      this.wsinitrp_mchidshift = decoder.readInt();
      this.wsinitrp_flags = decoder.readInt();
      this.wsinitrp_nettimeout = decoder.readInt();
      recv_size -= 16;
      if (recv_size > 0) {
         decoder.skipBytes(recv_size);
      }

   }

   public void _tmpostrecv(DataInputStream decoder, int recv_size) throws TPException, IOException {
      int descrim = decoder.readInt();
      recv_size -= 4;
      switch (descrim) {
         case 1:
            this.type = 1;
            break;
         case 8:
            this.type = 0;
            break;
         default:
            WTCLogger.logErrorWSRPCRQdescrim(descrim);
            throw new TPException(4, "Invalid WSRPCRQ descriminator: " + descrim);
      }

      if (this.type == 0) {
         this.postrecv_wsrpcrq(decoder, recv_size);
      } else {
         if (this.type != 1) {
            WTCLogger.logErrorWSRPCRQtype(this.type);
            throw new TPException(4, "Invalid WSRPCRQ type: " + this.type);
         }

         this.postrecv_wsinit_rp(decoder, recv_size);
      }

   }
}
