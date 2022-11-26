package org.omg.CosTransactions;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;

public final class UnavailableHolder implements Streamable {
   public Unavailable value = null;

   public UnavailableHolder() {
   }

   public UnavailableHolder(Unavailable initialValue) {
      this.value = initialValue;
   }

   public void _read(InputStream i) {
      this.value = UnavailableHelper.read(i);
   }

   public void _write(OutputStream o) {
      UnavailableHelper.write(o, this.value);
   }

   public TypeCode _type() {
      return UnavailableHelper.type();
   }
}
