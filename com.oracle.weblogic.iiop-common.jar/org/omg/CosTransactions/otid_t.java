package org.omg.CosTransactions;

import org.omg.CORBA.portable.IDLEntity;

public final class otid_t implements IDLEntity {
   public int formatID = 0;
   public int bqual_length = 0;
   public byte[] tid = null;

   public otid_t() {
   }

   public otid_t(int _formatID, int _bqual_length, byte[] _tid) {
      this.formatID = _formatID;
      this.bqual_length = _bqual_length;
      this.tid = _tid;
   }
}
