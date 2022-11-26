package weblogic.wtc.jatmi;

public final class FmlKey implements Comparable {
   private int _fldid;
   private int _occ;

   public FmlKey(int fldid, int occurrence) {
      this._fldid = fldid;
      this._occ = occurrence;
   }

   public FmlKey(FmlKey toCopy) {
      this._fldid = toCopy.get_fldid();
      this._occ = toCopy.get_occurrence();
   }

   public int get_fldid() {
      return this._fldid;
   }

   public int get_occurrence() {
      return this._occ;
   }

   /** @deprecated */
   @Deprecated
   public int get_occurance() {
      return this._occ;
   }

   public void set_occurrence(int occurrence) {
      this._occ = occurrence;
   }

   /** @deprecated */
   @Deprecated
   public void set_occurance(int occurrence) {
      this._occ = occurrence;
   }

   public boolean equals(Object obj) {
      if (obj != null && obj instanceof FmlKey) {
         if (obj == this) {
            return true;
         }

         FmlKey fmlkey = (FmlKey)obj;
         if (fmlkey.get_fldid() == this._fldid && fmlkey.get_occurrence() == this._occ) {
            return true;
         }
      }

      return false;
   }

   public String toString() {
      return new String(" fldid=" + this._fldid + "; occurrence=" + this._occ + ";");
   }

   public int hashCode() {
      int t = this._fldid << 3;
      t &= -268435456;
      int a = this._fldid;
      int b = this._fldid >> 10;
      a ^= b;
      a &= 1023;
      a <<= 18;
      return t | a | this._occ & 262143;
   }

   public int compareTo(Object obj) {
      if (obj == null) {
         return 1;
      } else {
         FmlKey k = (FmlKey)obj;
         int k_id;
         if ((k_id = k.get_fldid()) < this._fldid) {
            return 1;
         } else if (k_id > this._fldid) {
            return -1;
         } else {
            int k_occ;
            if ((k_occ = k.get_occurrence()) < this._occ) {
               return 1;
            } else {
               return k_occ > this._occ ? -1 : 0;
            }
         }
      }
   }
}
