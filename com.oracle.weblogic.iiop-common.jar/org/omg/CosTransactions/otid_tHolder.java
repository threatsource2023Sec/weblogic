package org.omg.CosTransactions;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;

public final class otid_tHolder implements Streamable {
   public otid_t value = null;

   public otid_tHolder() {
   }

   public otid_tHolder(otid_t initialValue) {
      this.value = initialValue;
   }

   public void _read(InputStream i) {
      this.value = otid_tHelper.read(i);
   }

   public void _write(OutputStream o) {
      otid_tHelper.write(o, this.value);
   }

   public TypeCode _type() {
      return otid_tHelper.type();
   }
}
