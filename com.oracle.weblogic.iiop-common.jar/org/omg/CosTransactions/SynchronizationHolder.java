package org.omg.CosTransactions;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;

public final class SynchronizationHolder implements Streamable {
   public Synchronization value = null;

   public SynchronizationHolder() {
   }

   public SynchronizationHolder(Synchronization initialValue) {
      this.value = initialValue;
   }

   public void _read(InputStream i) {
      this.value = SynchronizationHelper.read(i);
   }

   public void _write(OutputStream o) {
      SynchronizationHelper.write(o, this.value);
   }

   public TypeCode _type() {
      return SynchronizationHelper.type();
   }
}
