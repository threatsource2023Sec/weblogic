package weblogic.wtc.jatmi;

import com.bea.core.jatmi.common.Utilities;
import com.bea.core.jatmi.common.ntrace;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public final class TdomValsTcb extends tcb {
   private int dv_version = 0;
   private int dv_descrim = 0;
   private String dv_srvc = null;
   private String dv_src_domain = null;
   private String dv_orig_domain = null;
   private String dv_dest_domain = null;
   private transient byte[] myScratch;
   public static final int TMMSG_TDOM_VALS_VERSION = 1;
   public static final int TMMSG_TDOM_VALS_DOMS = 2;
   public static final int TMMSG_TDOM_VALS_SRVC = 1;
   public static final short TMMSG_ROUTE_DOMS_INCLUDED = 1;
   public static final short TMMSG_CALLOUT_DOMS_INCLUDED = 1;

   public TdomValsTcb() {
      super((short)17);
      this.dv_version = 1;
   }

   private int roundup4(int a) {
      return a + 3 & -4;
   }

   public String getOrigDomain() {
      return this.dv_orig_domain;
   }

   public void setOrigDomain(String dom) {
      this.dv_orig_domain = dom;
   }

   public String getSrcDomain() {
      return this.dv_src_domain;
   }

   public void setSrcDomain(String dom) {
      this.dv_src_domain = dom;
   }

   public String getDestDomain() {
      return this.dv_dest_domain;
   }

   public void setDestDomain(String dom) {
      this.dv_dest_domain = dom;
   }

   public String getSrvc() {
      return this.dv_srvc;
   }

   public void setSrvc(String svc) {
      this.dv_srvc = svc;
   }

   public int getDescrim() {
      return this.dv_descrim;
   }

   public void setDescrim(int newDescrim) {
      this.dv_descrim = newDescrim;
   }

   public boolean prepareForCache() {
      this.dv_version = 0;
      this.dv_descrim = 0;
      this.dv_srvc = null;
      this.dv_src_domain = null;
      this.dv_orig_domain = null;
      this.dv_dest_domain = null;
      return true;
   }

   public void _tmpresend(DataOutputStream encoder, tch myheader) throws TPException, IOException {
      boolean traceEnabled = ntrace.isTraceEnabled(4);
      if (traceEnabled) {
         ntrace.doTrace("[/TdomValsTcb/_tmpresend/" + myheader);
      }

      int calculated_size = myheader.getHeaderLen();
      calculated_size += 8;
      if (this.dv_descrim == 1) {
         calculated_size += Utilities.xdr_length_string(this.dv_srvc);
      } else if (this.dv_descrim == 2) {
         calculated_size += Utilities.xdr_length_string(this.dv_src_domain) + Utilities.xdr_length_string(this.dv_orig_domain) + Utilities.xdr_length_string(this.dv_dest_domain);
      }

      myheader.setLen(calculated_size);

      try {
         if (traceEnabled) {
            ntrace.doTrace("/TdomValsTcb/_tmpresend/10");
         }

         encoder.writeInt(this.dv_version);
         encoder.writeInt(this.dv_descrim);
         if (this.dv_descrim == 1) {
            Utilities.xdr_encode_string(encoder, this.dv_srvc);
         } else if (this.dv_descrim == 2) {
            Utilities.xdr_encode_string(encoder, this.dv_src_domain);
            Utilities.xdr_encode_string(encoder, this.dv_orig_domain);
            Utilities.xdr_encode_string(encoder, this.dv_dest_domain);
         }
      } catch (IOException var7) {
         if (traceEnabled) {
            ntrace.doTrace("*]/TdomValsTcb/_tmpresend/20");
         }

         throw var7;
      }

      if (traceEnabled) {
         ntrace.doTrace("]/TdomValsTcb/_tmpresend/30");
      }

   }

   public final int _tmpostrecv(DataInputStream decoder, int recv_size, int hint_index) throws IOException {
      if (this.myScratch == null) {
         this.myScratch = new byte[150];
      }

      this.dv_version = decoder.readInt();
      this.dv_descrim = decoder.readInt();
      if (this.dv_descrim == 1) {
         this.dv_srvc = Utilities.xdr_decode_string(decoder, this.myScratch);
      } else if (this.dv_descrim == 2) {
         this.dv_src_domain = Utilities.xdr_decode_string(decoder, this.myScratch);
         this.dv_orig_domain = Utilities.xdr_decode_string(decoder, this.myScratch);
         this.dv_dest_domain = Utilities.xdr_decode_string(decoder, this.myScratch);
      }

      return 0;
   }
}
