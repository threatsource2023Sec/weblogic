package org.omg.CosTransactions;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;

public final class CurrentHolder implements Streamable {
   public Current value = null;

   public CurrentHolder() {
   }

   public CurrentHolder(Current initialValue) {
      this.value = initialValue;
   }

   public void _read(InputStream i) {
      this.value = CurrentHelper.read(i);
   }

   public void _write(OutputStream o) {
      CurrentHelper.write(o, this.value);
   }

   public TypeCode _type() {
      return CurrentHelper.type();
   }
}
