package org.omg.CosTransactions;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;

public final class NotSubtransactionHolder implements Streamable {
   public NotSubtransaction value = null;

   public NotSubtransactionHolder() {
   }

   public NotSubtransactionHolder(NotSubtransaction initialValue) {
      this.value = initialValue;
   }

   public void _read(InputStream i) {
      this.value = NotSubtransactionHelper.read(i);
   }

   public void _write(OutputStream o) {
      NotSubtransactionHelper.write(o, this.value);
   }

   public TypeCode _type() {
      return NotSubtransactionHelper.type();
   }
}
