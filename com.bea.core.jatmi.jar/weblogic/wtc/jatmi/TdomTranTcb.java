package weblogic.wtc.jatmi;

import com.bea.core.jatmi.common.Utilities;
import com.bea.core.jatmi.common.ntrace;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import javax.transaction.xa.Xid;

public final class TdomTranTcb extends tcb {
   private int domtran_trantimeout;
   private int domtran_nwtranidlen;
   private byte[] domtran_nwtranidid;
   private String domtran_nwtranidparent;
   private transient byte[] myScratch;

   public TdomTranTcb() {
      super((short)10);
   }

   public TdomTranTcb(Xid tid, int trantime, String tranidparent) {
      super((short)10);
      Txid txid = new Txid(tid.getGlobalTransactionId());
      this.domtran_trantimeout = trantime;
      this.domtran_nwtranidid = txid.getTxid();
      this.domtran_nwtranidlen = this.domtran_nwtranidid.length;
      this.domtran_nwtranidparent = tranidparent;
   }

   public TdomTranTcb(Txid txid) {
      super((short)10);
      this.domtran_nwtranidid = txid.getTxid();
      this.domtran_nwtranidlen = this.domtran_nwtranidid.length;
   }

   public String getNwtranidparent() {
      return this.domtran_nwtranidparent;
   }

   public int getTransactionTimeout() {
      return this.domtran_trantimeout;
   }

   public byte[] getGlobalTransactionId() {
      return this.domtran_nwtranidid;
   }

   public void setNwtranidparent(String parent) {
      this.domtran_nwtranidparent = parent;
   }

   public void setTransactionTimeout(int timeout) {
      this.domtran_trantimeout = timeout;
   }

   public void setGlobalTransactionId(byte[] gtrid) {
      if (gtrid == null) {
         this.domtran_nwtranidlen = 0;
         this.domtran_nwtranidid = null;
      } else {
         this.domtran_nwtranidid = new byte[32];
         this.domtran_nwtranidlen = 32;

         for(int lcv = 0; lcv < 32; ++lcv) {
            if (lcv < gtrid.length) {
               this.domtran_nwtranidid[lcv] = gtrid[lcv];
            } else {
               this.domtran_nwtranidid[lcv] = 0;
            }
         }
      }

   }

   public boolean prepareForCache() {
      return false;
   }

   public void _tmpresend(DataOutputStream encoder, tch myheader) throws TPException, IOException {
      boolean traceEnabled = ntrace.isTraceEnabled(4);
      if (traceEnabled) {
         ntrace.doTrace("[/TdomTranTcb/_tmpresend/" + myheader);
      }

      int calculated_size = myheader.getHeaderLen();
      int output_data_size = false;
      calculated_size += 144 + Utilities.xdr_length_string(this.domtran_nwtranidparent);
      myheader.setLen(calculated_size);

      try {
         encoder.writeInt(this.domtran_trantimeout);
         encoder.writeInt(this.domtran_nwtranidlen);
         encoder.writeInt(0);
         encoder.writeInt(0);

         for(int lcv = 0; lcv < 32; ++lcv) {
            byte anInteger;
            if (lcv < this.domtran_nwtranidid.length) {
               anInteger = this.domtran_nwtranidid[lcv];
            } else {
               anInteger = 0;
            }

            encoder.writeInt(anInteger);
         }

         Utilities.xdr_encode_string(encoder, this.domtran_nwtranidparent);
      } catch (IOException var10) {
         if (traceEnabled) {
            ntrace.doTrace("*]/TdomTranTcb/_tmpresend/10/" + var10);
         }

         throw var10;
      }

      if (traceEnabled) {
         ntrace.doTrace("]/TdomTranTcb/_tmpresend/20/");
      }

   }

   public final int _tmpostrecv(DataInputStream decoder, int recv_size, int hint_index) throws IOException {
      boolean traceEnabled = ntrace.isTraceEnabled(4);
      if (traceEnabled) {
         ntrace.doTrace("[/TdomTranTcb/_tmpostrecv/" + recv_size + "/" + hint_index);
      }

      if (this.myScratch == null) {
         this.myScratch = new byte[150];
      }

      this.domtran_trantimeout = decoder.readInt();
      this.domtran_nwtranidlen = decoder.readInt();
      decoder.readInt();
      decoder.readInt();
      this.domtran_nwtranidid = new byte[32];

      int lcv;
      for(lcv = 0; lcv < this.domtran_nwtranidid.length; ++lcv) {
         this.domtran_nwtranidid[lcv] = (byte)decoder.readInt();
      }

      while(lcv < 32) {
         this.domtran_nwtranidid[lcv] = 0;
         decoder.readInt();
         ++lcv;
      }

      this.domtran_nwtranidlen = 32;
      this.domtran_nwtranidparent = Utilities.xdr_decode_string(decoder, this.myScratch);
      if (traceEnabled) {
         ntrace.doTrace("]/TdomTranTcb/_tmpostrecv/10/0");
      }

      return 0;
   }

   public String toString() {
      return new String(this.domtran_trantimeout + ":" + this.domtran_nwtranidlen + ":" + Utilities.prettyByteArray(this.domtran_nwtranidid) + ":" + this.domtran_nwtranidparent);
   }
}
