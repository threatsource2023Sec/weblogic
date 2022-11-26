package org.omg.CosTransactions;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;

public final class NoTransactionHolder implements Streamable {
   public NoTransaction value = null;

   public NoTransactionHolder() {
   }

   public NoTransactionHolder(NoTransaction initialValue) {
      this.value = initialValue;
   }

   public void _read(InputStream i) {
      this.value = NoTransactionHelper.read(i);
   }

   public void _write(OutputStream o) {
      NoTransactionHelper.write(o, this.value);
   }

   public TypeCode _type() {
      return NoTransactionHelper.type();
   }
}
