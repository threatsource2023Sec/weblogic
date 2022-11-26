package weblogic.wtc.jatmi;

import com.bea.core.jatmi.common.ntrace;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public final class MetaTcb extends tcb {
   private TypedFML32 mybuf = new TypedFML32();
   private String ecid;

   public MetaTcb() {
      super((short)19);
   }

   public boolean prepareForCache() {
      return false;
   }

   public TypedFML32 getBuf() {
      if (this.mybuf == null) {
         this.mybuf = new TypedFML32();
      }

      return this.mybuf;
   }

   public void setBuf(TypedFML32 mybuf) {
      this.mybuf = mybuf;
   }

   public void _tmpresend(DataOutputStream encoder, tch myheader) throws TPException, IOException {
      boolean traceEnabled = ntrace.isTraceEnabled(4);
      int level = ntrace.getTraceLevel();
      if (traceEnabled) {
         ntrace.doTrace("[/MetaTcb/_tmpresend/" + myheader);
      }

      int calculated_size = myheader.getHeaderLen();
      int send_size = false;
      if (this.mybuf == null) {
         this.mybuf = new TypedFML32();
      }

      try {
         this.mybuf.Fchg(new FmlKey(197775061, 0), this.ecid);
      } catch (Ferror var12) {
         if (traceEnabled) {
            ntrace.doTrace("*]/MetaTcb/_tmpresend/5/" + var12);
         }

         throw new TPException(12, "Unable to add field " + var12);
      }

      int send_size;
      try {
         int initial_size = encoder.size();
         this.mybuf._tmpresend(encoder);
         int new_size = encoder.size();
         send_size = new_size - initial_size;
      } catch (IOException var10) {
         if (traceEnabled) {
            ntrace.doTrace("*]/MetaTcb/_tmpresend/10/" + var10);
         }

         throw var10;
      } catch (TPException var11) {
         if (traceEnabled) {
            ntrace.doTrace("*]/MetaTcb/_tmpresend/20/" + var11);
         }

         throw var11;
      }

      calculated_size += send_size;
      myheader.setLen(calculated_size);
      if (traceEnabled) {
         ntrace.doTrace("]/MetaTcb/_tmpresend/30/");
      }

   }

   public final int _tmpostrecv(DataInputStream decoder, int recv_size, int hint_index) throws IOException {
      boolean traceEnabled = ntrace.isTraceEnabled(4);
      if (traceEnabled) {
         ntrace.doTrace("[/MetaTcb/_tmpostrecv/" + decoder + "/" + recv_size + "/" + hint_index);
      }

      if (this.mybuf == null) {
         this.mybuf = new TypedFML32();
      }

      try {
         this.mybuf._tmpostrecv(decoder, recv_size);

         try {
            FmlKey key = new FmlKey(197775061, 0);
            this.ecid = (String)((String)this.mybuf.Fget(key));
         } catch (Ferror var6) {
            if (traceEnabled) {
               ntrace.doTrace("]/MetaTcb/_tmpostrecv/10/-1/" + var6);
            }

            return -1;
         }
      } catch (TPException var7) {
         if (traceEnabled) {
            ntrace.doTrace("]/MetaTcb/_tmpostrecv/40/-1/" + var7);
         }

         return -1;
      }

      if (traceEnabled) {
         ntrace.doTrace("]/MetaTcb/_tmpostrecv/30/0");
      }

      return 0;
   }

   public String getECID() {
      return this.ecid;
   }

   public void setECID(String ecid) {
      if (ecid != null) {
         this.ecid = new String(ecid);
      }

   }
}
