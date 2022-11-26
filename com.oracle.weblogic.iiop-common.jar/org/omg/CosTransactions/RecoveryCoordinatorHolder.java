package org.omg.CosTransactions;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;

public final class RecoveryCoordinatorHolder implements Streamable {
   public RecoveryCoordinator value = null;

   public RecoveryCoordinatorHolder() {
   }

   public RecoveryCoordinatorHolder(RecoveryCoordinator initialValue) {
      this.value = initialValue;
   }

   public void _read(InputStream i) {
      this.value = RecoveryCoordinatorHelper.read(i);
   }

   public void _write(OutputStream o) {
      RecoveryCoordinatorHelper.write(o, this.value);
   }

   public TypeCode _type() {
      return RecoveryCoordinatorHelper.type();
   }
}
