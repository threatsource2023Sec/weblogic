package org.omg.CosTransactions;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;

public final class NotPreparedHolder implements Streamable {
   public NotPrepared value = null;

   public NotPreparedHolder() {
   }

   public NotPreparedHolder(NotPrepared initialValue) {
      this.value = initialValue;
   }

   public void _read(InputStream i) {
      this.value = NotPreparedHelper.read(i);
   }

   public void _write(OutputStream o) {
      NotPreparedHelper.write(o, this.value);
   }

   public TypeCode _type() {
      return NotPreparedHelper.type();
   }
}
