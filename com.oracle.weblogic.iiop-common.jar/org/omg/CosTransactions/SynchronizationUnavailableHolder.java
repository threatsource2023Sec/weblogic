package org.omg.CosTransactions;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;

public final class SynchronizationUnavailableHolder implements Streamable {
   public SynchronizationUnavailable value = null;

   public SynchronizationUnavailableHolder() {
   }

   public SynchronizationUnavailableHolder(SynchronizationUnavailable initialValue) {
      this.value = initialValue;
   }

   public void _read(InputStream i) {
      this.value = SynchronizationUnavailableHelper.read(i);
   }

   public void _write(OutputStream o) {
      SynchronizationUnavailableHelper.write(o, this.value);
   }

   public TypeCode _type() {
      return SynchronizationUnavailableHelper.type();
   }
}
