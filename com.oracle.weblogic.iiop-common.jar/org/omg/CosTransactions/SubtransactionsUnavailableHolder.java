package org.omg.CosTransactions;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;

public final class SubtransactionsUnavailableHolder implements Streamable {
   public SubtransactionsUnavailable value = null;

   public SubtransactionsUnavailableHolder() {
   }

   public SubtransactionsUnavailableHolder(SubtransactionsUnavailable initialValue) {
      this.value = initialValue;
   }

   public void _read(InputStream i) {
      this.value = SubtransactionsUnavailableHelper.read(i);
   }

   public void _write(OutputStream o) {
      SubtransactionsUnavailableHelper.write(o, this.value);
   }

   public TypeCode _type() {
      return SubtransactionsUnavailableHelper.type();
   }
}
