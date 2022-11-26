package org.omg.CosTransactions;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;

public final class InactiveHolder implements Streamable {
   public Inactive value = null;

   public InactiveHolder() {
   }

   public InactiveHolder(Inactive initialValue) {
      this.value = initialValue;
   }

   public void _read(InputStream i) {
      this.value = InactiveHelper.read(i);
   }

   public void _write(OutputStream o) {
      InactiveHelper.write(o, this.value);
   }

   public TypeCode _type() {
      return InactiveHelper.type();
   }
}
