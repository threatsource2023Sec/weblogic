package org.omg.CosTransactions;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;

public final class SubtransactionAwareResourceHolder implements Streamable {
   public SubtransactionAwareResource value = null;

   public SubtransactionAwareResourceHolder() {
   }

   public SubtransactionAwareResourceHolder(SubtransactionAwareResource initialValue) {
      this.value = initialValue;
   }

   public void _read(InputStream i) {
      this.value = SubtransactionAwareResourceHelper.read(i);
   }

   public void _write(OutputStream o) {
      SubtransactionAwareResourceHelper.write(o, this.value);
   }

   public TypeCode _type() {
      return SubtransactionAwareResourceHelper.type();
   }
}
