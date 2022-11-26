package org.omg.CosTransactions;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;

public final class TransIdentityHolder implements Streamable {
   public TransIdentity value = null;

   public TransIdentityHolder() {
   }

   public TransIdentityHolder(TransIdentity initialValue) {
      this.value = initialValue;
   }

   public void _read(InputStream i) {
      this.value = TransIdentityHelper.read(i);
   }

   public void _write(OutputStream o) {
      TransIdentityHelper.write(o, this.value);
   }

   public TypeCode _type() {
      return TransIdentityHelper.type();
   }
}
