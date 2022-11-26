package org.omg.CosTransactions;

import org.omg.CORBA.portable.IDLEntity;

public final class TransIdentity implements IDLEntity {
   public Coordinator coord = null;
   public Terminator term = null;
   public otid_t otid = null;

   public TransIdentity() {
   }

   public TransIdentity(Coordinator _coord, Terminator _term, otid_t _otid) {
      this.coord = _coord;
      this.term = _term;
      this.otid = _otid;
   }
}
