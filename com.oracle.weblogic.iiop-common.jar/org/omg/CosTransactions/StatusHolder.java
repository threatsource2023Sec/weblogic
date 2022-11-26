package org.omg.CosTransactions;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;

public final class StatusHolder implements Streamable {
   public Status value = null;

   public StatusHolder() {
   }

   public StatusHolder(Status initialValue) {
      this.value = initialValue;
   }

   public void _read(InputStream i) {
      this.value = StatusHelper.read(i);
   }

   public void _write(OutputStream o) {
      StatusHelper.write(o, this.value);
   }

   public TypeCode _type() {
      return StatusHelper.type();
   }
}
