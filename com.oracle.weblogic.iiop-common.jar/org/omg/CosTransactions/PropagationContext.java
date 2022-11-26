package org.omg.CosTransactions;

import org.omg.CORBA.Any;
import org.omg.CORBA.portable.IDLEntity;

public final class PropagationContext implements IDLEntity {
   public int timeout = 0;
   public TransIdentity current = null;
   public TransIdentity[] parents = null;
   public Any implementation_specific_data = null;

   public PropagationContext() {
   }

   public PropagationContext(int _timeout, TransIdentity _current, TransIdentity[] _parents, Any _implementation_specific_data) {
      this.timeout = _timeout;
      this.current = _current;
      this.parents = _parents;
      this.implementation_specific_data = _implementation_specific_data;
   }
}
