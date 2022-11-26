package weblogic.wtc.jatmi;

public final class Txid {
   public static final int MAXTXIDSIZE = 32;
   private byte[] myGlobalXid = new byte[32];

   public Txid(byte[] aGlobalXid) {
      int lcv;
      if (aGlobalXid == null) {
         for(lcv = 0; lcv < 32; ++lcv) {
            this.myGlobalXid[lcv] = 0;
         }
      } else {
         for(lcv = 0; lcv < aGlobalXid.length; ++lcv) {
            this.myGlobalXid[lcv] = aGlobalXid[lcv];
         }

         while(lcv < 32) {
            this.myGlobalXid[lcv] = 0;
            ++lcv;
         }
      }

   }

   public byte[] getTxid() {
      return this.myGlobalXid;
   }

   public boolean equals(Object ct) {
      if (ct == null) {
         return false;
      } else {
         Txid rct = (Txid)ct;
         byte[] rct_gxid;
         if ((rct_gxid = rct.getTxid()) == null && this.myGlobalXid == null) {
            return true;
         } else if ((rct_gxid != null || this.myGlobalXid == null) && (rct_gxid == null || this.myGlobalXid != null)) {
            if (rct_gxid.length != this.myGlobalXid.length) {
               return false;
            } else {
               for(int lcv = 0; lcv < rct_gxid.length; ++lcv) {
                  if (rct_gxid[lcv] != this.myGlobalXid[lcv]) {
                     return false;
                  }
               }

               return true;
            }
         } else {
            return false;
         }
      }
   }

   public int hashCode() {
      int code = 0;
      if (this.myGlobalXid == null) {
         return 0;
      } else {
         int words = this.myGlobalXid.length / 4;

         for(int lcv = 0; lcv < words; ++lcv) {
            int tmpcode = this.myGlobalXid[lcv * 4 + 3] << 24 | this.myGlobalXid[lcv * 4 + 2] << 16 | this.myGlobalXid[lcv * 4 + 1] << 8 | this.myGlobalXid[lcv * 4];
            code ^= tmpcode;
         }

         if (code < 0) {
            code = -code;
         }

         return code;
      }
   }

   public String toString() {
      String ret = Integer.toString(this.myGlobalXid.length) + ":";
      int i = 0;

      while(i < this.myGlobalXid.length) {
         ret = ret + Integer.toString(this.myGlobalXid[i]);
         ++i;
         if (i < this.myGlobalXid.length) {
            ret = ret + ",";
         }
      }

      return ret;
   }
}
