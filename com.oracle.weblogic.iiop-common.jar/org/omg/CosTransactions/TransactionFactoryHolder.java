package org.omg.CosTransactions;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;

public final class TransactionFactoryHolder implements Streamable {
   public TransactionFactory value = null;

   public TransactionFactoryHolder() {
   }

   public TransactionFactoryHolder(TransactionFactory initialValue) {
      this.value = initialValue;
   }

   public void _read(InputStream i) {
      this.value = TransactionFactoryHelper.read(i);
   }

   public void _write(OutputStream o) {
      TransactionFactoryHelper.write(o, this.value);
   }

   public TypeCode _type() {
      return TransactionFactoryHelper.type();
   }
}
