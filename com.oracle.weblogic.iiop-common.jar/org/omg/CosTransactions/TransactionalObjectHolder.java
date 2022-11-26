package org.omg.CosTransactions;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;

public final class TransactionalObjectHolder implements Streamable {
   public TransactionalObject value = null;

   public TransactionalObjectHolder() {
   }

   public TransactionalObjectHolder(TransactionalObject initialValue) {
      this.value = initialValue;
   }

   public void _read(InputStream i) {
      this.value = TransactionalObjectHelper.read(i);
   }

   public void _write(OutputStream o) {
      TransactionalObjectHelper.write(o, this.value);
   }

   public TypeCode _type() {
      return TransactionalObjectHelper.type();
   }
}
